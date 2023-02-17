package controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import models.Model;
import models.Strategy;
import utils.ChartPlot;
import utils.ChartPlotCalculatorUtil;
import views.GUIView;

/**
 * Controller class for managing the data flow between GUI and model class.
 * This class implements both the controller and features interface and has all the features
 * functionality built.
 */
public class GUIController implements Controller, Features {
  private final Model model;
  private final GUIView guiView;


  public GUIController(Model model, GUIView guiView) {
    this.model = model;
    this.guiView = guiView;
    this.guiView.addFeatures(this);
    initialize();
  }

  private void initialize() {
    try {
      if(model.getTotalStocks() == 0) {
        guiView.showProcessingPopup();
        model.getAllStocksFromAPI();
        guiView.closeProcessingPopup();
      }
      List<String> brokers = model.getAllBrokers();
      guiView.setBrokers(brokers);
    } catch (SQLException e) {
      guiView.showMessageDialog("Failed to fetch stocks from database");
      guiView.closeProcessingPopup();
    }
  }

  @Override
  public void start() {
    return;
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void login(String userName, String password) {
    boolean loggedIn = false;
    try {
      loggedIn = model.loginUser(userName, password);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return;
    }

    if (loggedIn) {
      try {
        guiView.openMainMenuLayout(model.getUser().getUserName(), model.getUser().getUserId(),
                new ArrayList<>(model.getAllPortfolios()));
      } catch (SQLException e) {
        guiView.showMessageDialog(e.getMessage());
        return;
      }
      guiView.clearLoginPageInputs();
    } else {
      guiView.showNoUserFoundPrompt();
    }
  }

  @Override
  public void signUp(String userName, String password, List<String> brokers) {
    boolean signUp = false;
    try {
      signUp = model.signUp(userName, password);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return;
    }

    if(signUp) {
      for(String broker: brokers) {
        try {
          model.addBroker(model.getUser().getUserId(), broker);
        } catch (SQLException e) {
          guiView.showMessageDialog(e.getMessage());
        }
      }

      List<String> portfolios = null;
      try {
        portfolios = model.getAllPortfolios();
      } catch (SQLException e) {
        guiView.showMessageDialog(e.getMessage());
        return;
      }
      guiView.openMainMenuLayout(model.getUser().getUserName(), model.getUser().getUserId(),
              new ArrayList<>(portfolios));
      guiView.clearLoginPageInputs();
    } else {
      guiView.showMessageDialog("Failed to sign up, please try again.");
    }
  }

  @Override
  public void logout() {
    model.setUser(null);
    guiView.switchLayout("Login");
  }

  @Override
  public void analyzePortfolio(String portfolioName) {
    List<List<String>> composition = null;
    try {
      composition = model.getPortfolioComposition(model.getUser().getUserId(),
              portfolioName, new Date());
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return;
    }
    guiView.openPortfolioDetails(portfolioName, composition);
  }

  @Override
  public void requestCreatePortfolio() {
    try {
      Map<String, Double> brokerCommissions = model.getBrokerCommissions(model.getUser().getUserId());
      guiView.requestCreatePortfolio(brokerCommissions);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void goToMainMenu() {
    List<String> portfolios;
    try {
      portfolios = new ArrayList<>(model.getAllPortfolios());
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return;
    }
    guiView.openMainMenuLayout(model.getUser().getUserName(), model.getUser().getUserId(), portfolios);
    guiView.clearPortfolioDetailsPage();
    guiView.clearAnalyzeStocksPage();
    guiView.clearStockGraphProcessing();
  }

  @Override
  public void getValueOnADate(String portfolioName) {
    String dateInput = guiView.getDateInput();
    if (dateInput != null) {
      try {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        double value = model.getPortfolioValueByDate(model.getUser().getUserId(),
                portfolioName, format.parse(dateInput));
        guiView.showValueOnADate(format.parse(dateInput), value);
      } catch (IllegalArgumentException | ParseException e) {
        guiView.showWrongDateInputPopup();
      } catch (SQLException e) {
        guiView.showMessageDialog(e.getMessage());
      }
    }
  }

  @Override
  public void getCostBasis(String portfolioName) {
    String dateInput = guiView.getDateInput();
    if (dateInput != null) {
      try {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        Date date = format.parse(dateInput);
        double value = model.getCostBasis(model.getUser().getUserId(), portfolioName, date);
        guiView.showCostBasis(date, value);
      } catch (ParseException e) {
        guiView.showWrongDateInputPopup();
      } catch (SQLException e) {
        guiView.showMessageDialog(e.getMessage());
      }
    }
  }

  @Override
  public void showComposition(String portfolioName) {
    String dateInput = guiView.getDateInput();
    if (dateInput != null) {
      try {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        Date date = format.parse(dateInput);
        List<List<String>> composition = model.getPortfolioComposition(model.getUser().getUserId(), portfolioName, date);
        guiView.showComposition(composition);
      } catch (ParseException e) {
        guiView.showWrongDateInputPopup();
      } catch (SQLException e) {
        guiView.showMessageDialog(e.getMessage());
      }
    }
  }

  @Override
  public void requestModifyPortfolio(String portfolioName) {
    Map<String, Double> portfolioElements =
            null;
    try {
      portfolioElements = model.getPortfolioElementsForModify(model.getUser().getUserId(), portfolioName);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return;
    }

    List<String> stockModels = null;
    try {
      stockModels = new ArrayList<>(model.getAllStocks());
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return;
    }

    guiView.showModifyPortfolioPopup(portfolioName, portfolioElements, stockModels);
  }

  @Override
  public void requestMoreStockInput() {
    guiView.requestMoreStockInput();
  }

  @Override
  public void requestAddStrategy(String portfolioName) {
    try {
      guiView.requestAddStrategy(portfolioName,
              new ArrayList<>(model.getAllStocks()),
              new ArrayList<>(EnumSet.allOf(Strategy.class)));
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
    }
  }

  @Override
  public void requestMoreStockInputForStrategy() {
    guiView.requestMoreStockInputForStrategy();
  }

  @Override
  public void packLayout() {
    guiView.packLayout();
  }

  @Override
  public void requestCreateFlexiblePortfolioWithoutStrategy(Map<String, Double> brokerCommissions) {
    try {
      guiView.requestCreateFlexiblePortfolioWithoutStrategy(new
              ArrayList<>(model.getAllStocks()), brokerCommissions);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
    }
  }

  @Override
  public void showGraph(String portfolioName) {
    String[] dates = guiView.getStartEndDateInput();

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);

    Date startDate;
    Date endDate;

    if (dates.length != 2) {
      return;
    }

    try {
      startDate = format.parse(dates[0]);
    } catch (ParseException e) {
      guiView.showWrongDateInputPopup();
      return;
    }

    try {
      endDate = format.parse(dates[1]);
    } catch (ParseException e) {
      guiView.showWrongDateInputPopup();
      return;
    }

    if (startDate.compareTo(endDate) > 0) {
      guiView.showWrongDateInputPopup();
      return;
    }

    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);
    if (startDate.compareTo(today.getTime()) >= 0 || endDate.compareTo(today.getTime()) >= 0) {
      guiView.showWrongDateInputPopup();
      return;
    }

    ChartPlot chartPlot = null;
    try {
      chartPlot = model.getPerformanceForPortfolio(startDate, endDate,
              portfolioName);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return;
    }
    guiView.showBarChart(ChartPlotCalculatorUtil.getDateLabel(startDate,
                    chartPlot.getDateRangeType()),
            ChartPlotCalculatorUtil.getDateLabel(endDate, chartPlot.getDateRangeType()),
            portfolioName,
            chartPlot);
  }

  private boolean saveStrategyHelper(String portfolioName, String strategyType,
                                     String strategyName, String amountText, String commission,
                                     String tStartDateText, String tEndDateText,
                                     String tFrequencyCountText, List<String> stockNames,
                                     List<String> weights) {
    Date startDate;
    Date endDate;
    int frequency;

    double commissionDouble;
    double amount;

    List<Double> weightsList = new ArrayList<>();

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    try {
      startDate = format.parse(tStartDateText);
    } catch (ParseException e) {
      guiView.showWrongDateInputPopup();
      return false;
    }

    if (tEndDateText.equals("")) {
      endDate = null;
    } else {
      try {
        endDate = format.parse(tEndDateText);
      } catch (ParseException e) {
        guiView.showWrongDateInputPopup();
        return false;
      }
    }

    try {
      commissionDouble = Double.parseDouble(commission);
    } catch (NumberFormatException e) {
      guiView.showIncorrectCommissionPopup();
      return false;
    }

    try {
      amount = Double.parseDouble(amountText);
    } catch (NumberFormatException e) {
      guiView.showIncorrectAmountPopup();
      return false;
    }

    try {
      frequency = Integer.parseInt(tFrequencyCountText);
    } catch (NumberFormatException e) {
      guiView.showIncorrectFrequencyPopup();
      return false;
    }

    for (String weight : weights) {
      try {
        double weightDouble = Double.parseDouble(weight);
        weightsList.add(weightDouble);
      } catch (NumberFormatException e) {
        guiView.showIncorrectWeightPopup();
        return false;
      }
    }

    double totalWeight = 0;
    for (double weight : weightsList) {
      totalWeight += weight;
    }

    if (Math.abs(totalWeight - 100) > 0.02) {
      guiView.showIncorrectWeightPopup();
      return false;
    }

    int portfolioId = 0;
    try {
      portfolioId = model.getPortfolioId(model.getUser().getUserId(), portfolioName);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return false;
    }

    int strategyId = 0;
    try {
      strategyId = model.addStrategy(strategyName, portfolioId, startDate,
              endDate, amount, commissionDouble, frequency);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      return false;
    }

    guiView.showAddStrategyProcessing();
    for(int i = 0; i < stockNames.size(); i++) {
      try {
        model.fetchStockData(stockNames.get(i), endDate);
        model.addStrategyElement(strategyId, stockNames.get(i), weightsList.get(i));
      } catch (SQLException e) {
        guiView.clearAddStrategyProcessing();
        guiView.showMessageDialog(e.getMessage());
        return false;
      }
    }
    try {
      model.runStrategy(strategyId);
    } catch (SQLException e) {
      guiView.clearAddStrategyProcessing();
      guiView.showMessageDialog(e.getMessage());
    }
    guiView.clearAddStrategyProcessing();
    return true;
  }

  @Override
  public void saveStrategy(String portfolioName, String strategyType,
                           String strategyName, String amountText, String commission,
                           String tStartDateText, String tEndDateText,
                           String tFrequencyCountText, List<String> stockNames,
                           List<String> weights) {
    if (saveStrategyHelper(portfolioName, strategyType,
            strategyName, amountText,
            commission, tStartDateText,
            tEndDateText, tFrequencyCountText,
            stockNames, weights)) {
      guiView.closeAddStrategyPopup();
      guiView.showAddStrategySuccessPopup();
    }
  }

  @Override
  public void createFlexiblePortfolioWithoutStrategy(String portfolioName,
                                                     List<String> stockNames,
                                                     List<String> quantities,
                                                     List<String> commissions,
                                                     List<String> dates) {
    List<Integer> quantitiesInt = new ArrayList<>();
    List<Double> commissionsDouble = new ArrayList<>();
    List<Double> prices = new ArrayList<>();
    List<Date> transactionDates = new ArrayList<>();
    int portfolioId = 0;

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    for (String date : dates) {
      try {
        Date currentDate = format.parse(date);
        transactionDates.add(currentDate);
      } catch (ParseException e) {
        guiView.showWrongDateInputPopup();
        return;
      }
    }

    for (String quantity : quantities) {
      try {
        int quantityInt = Integer.parseInt(quantity);
        quantitiesInt.add(quantityInt);
      } catch (NumberFormatException e) {
        guiView.showIncorrectQuantityPopup();
        return;
      }
    }

    for (String commission : commissions) {
      try {
        double commissionDouble = Double.parseDouble(commission);
        commissionsDouble.add(commissionDouble);
      } catch (NumberFormatException e) {
        guiView.showIncorrectCommissionPopup();
        return;
      }
    }

    guiView.showCreatePortfolioProcessing();

    for (int i = 0; i < stockNames.size(); i++) {
      try {
        String stockName = stockNames.get(i);
        Date transactionDate = transactionDates.get(i);
        if (!model.doesPriceExistOnADate(stockName, transactionDate)) {
          guiView.showNoPriceOnDatePopup(stockName);
          guiView.clearCreatePortfolioProcessing();
          return;
        }
        double price = model.getStockPriceOnADate(stockName, transactionDate);
        prices.add(price);
      } catch (SQLException e) {
        guiView.showMessageDialog(e.getMessage());
        guiView.clearCreatePortfolioProcessing();
        return;
      }
    }

    try {
      portfolioId = model.createPortfolio(portfolioName);
    } catch (SQLException e) {
      guiView.clearCreatePortfolioProcessing();
      guiView.showMessageDialog(e.getMessage());
      return;
    }

    for (int i = 0; i < stockNames.size(); i++) {
      try {
        model.addTransaction(portfolioId, stockNames.get(i), "BUY", quantitiesInt.get(i),
                prices.get(i), transactionDates.get(i), commissionsDouble.get(i));
      } catch (SQLException e) {
        guiView.clearCreatePortfolioProcessing();
        guiView.showMessageDialog(e.getMessage());
        return;
      }
    }


    try {
      guiView.closeAddFlexiblePortfolioWithoutStrategyPopup(
              new ArrayList<>(model.getAllPortfolios()));
    } catch (SQLException e) {
      guiView.clearCreatePortfolioProcessing();
      guiView.showMessageDialog(e.getMessage());
    }

    guiView.clearCreatePortfolioProcessing();
  }

  @Override
  public void modifyPortfolio(String portfolioName, String stockTicker,
                              String quantity, String commission, String buySell, String date) {
    int quantityInt = 0;
    double commissionDouble = 0;
    Date transactionDate;
    try {
      quantityInt = Integer.parseInt(quantity);
    } catch (NumberFormatException e) {
      guiView.showIncorrectQuantityPopup();
      return;
    }

    try {
      commissionDouble = Double.parseDouble(commission);
    } catch (NumberFormatException e) {
      guiView.showIncorrectCommissionPopup();
      return;
    }

    try {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      format.setLenient(false);
      transactionDate = format.parse(date);
    } catch (ParseException e) {
      guiView.showWrongDateInputPopup();
      return;
    }


    guiView.showModifyProcessing();

    try {
      if (!model.doesPriceExistOnADate(stockTicker, transactionDate)) {
        guiView.showNoPriceOnDatePopup(stockTicker);
        return;
      }
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      guiView.clearModifyProcessing();
      return;
    }


    double price = 0;
    try {
      price = model.getStockPriceOnADate(stockTicker, transactionDate);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      guiView.clearModifyProcessing();
      return;
    }

    int portfolioId = 0;
    try {
       portfolioId = model.getPortfolioId(model.getUser().getUserId(), portfolioName);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      guiView.clearModifyProcessing();
      return;
    }
    try {
      model.addTransaction(portfolioId, stockTicker, buySell,
              quantityInt, price, transactionDate, commissionDouble);
      guiView.clearModifyProcessing();
      guiView.closeModifyPortfolioPopup();
      guiView.showModifyPortfolioSuccessPopup();
    } catch (SQLException e) {
      guiView.clearModifyProcessing();
      guiView.closeModifyPortfolioPopup();
      guiView.showMessageDialog(e.getMessage());
    }

    guiView.clearModifyProcessing();
  }

  @Override
  public void deletePortfolio(String portfolioName) {
    try {
      model.deletePortfolio(model.getUser().getUserId(), portfolioName);
      guiView.setPortfolios(new ArrayList<>(model.getAllPortfolios()));
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
    }
  }

  @Override
  public void showStocksGraph(String stockName, String startDate, String endDate) {
    guiView.showStockGraphProcessing();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);

    Date startDateGraph;
    Date endDateGraph;

    try {
      startDateGraph = format.parse(startDate);
    } catch (ParseException e) {
      guiView.showWrongDateInputPopup();
      guiView.clearStockGraphProcessing();
      return;
    }

    try {
      endDateGraph = format.parse(endDate);
    } catch (ParseException e) {
      guiView.showWrongDateInputPopup();
      guiView.clearStockGraphProcessing();
      return;
    }

    ChartPlot chartPlot = null;
    try {
      chartPlot = model.getPerformanceForStock(stockName, startDateGraph, endDateGraph);
      guiView.showStockBarChart(chartPlot);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
      guiView.clearStockGraphProcessing();
    }

  }

  @Override
  public void analyzeStocks() {
    try {
      List<String> stocks = model.getAllStocks();
      guiView.showAnalyzeStocks(stocks);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
    }
  }

  @Override
  public void requestUpdateProfile() {
    String[] profileDetails;
    try {
      profileDetails = model.getProfileDetails(model.getUser().getUserId());
      guiView.requestUpdateProfile(profileDetails);
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
    }
  }

  @Override
  public void updateProfile(String nickName, String phoneNumber, String password) {
    try {
      model.updateProfile(model.getUser().getUserId(), nickName, phoneNumber, password);
      guiView.showMessageDialog("Update Profile Success");
    } catch (SQLException e) {
      guiView.showMessageDialog(e.getMessage());
    }
  }

  @Override
  public void requestBrokersSelection() {
    guiView.requestBrokerSelection();
  }

  @Override
  public void closeChooseBrokerPanel(List<String> selectedItems) {
    guiView.chooseBrokers(selectedItems);
  }

}
