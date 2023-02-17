package models;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import utils.AlphaVantageModel;
import utils.ChartPlot;
import utils.ChartPlotCalculatorUtil;
import utils.DBHandler;
import utils.DateRange;
import utils.DateRangeCalculatorUtil;

public class Model {
  DBHandler handler;
  private User user;

  public Model(String userName, String password) throws SQLException {
    handler = new DBHandler(userName, password);
  }

  public boolean loginUser(String userName, String password) throws SQLException {
    this.user = handler.loginUser(userName, password);
    return this.user != null;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<String> getAllPortfolios() throws SQLException {
    List<String> portfolios = handler.getAllPortfolios(user.getUserId());
    return portfolios;
  }

  public boolean signUp(String userName, String password) throws SQLException {
    this.user = handler.signUpUser(userName, password);
    return this.user != null;
  }

  public int getTotalStocks() throws SQLException {
    return handler.getTotalStocks();
  }

  public List<String> getAllStocks() throws SQLException {
    return handler.getAllStocks();
  }

  public void getAllStocksFromAPI() throws SQLException {
    List<StockModel> stocks = new AlphaVantageModel().getSupportedStocks();
    handler.insertStocks(stocks);
  }

  public int createPortfolio(String portfolioName) throws SQLException {
    return handler.createPortfolio(portfolioName, user.getUserId(), new Date());
  }

  public boolean doesPriceExistOnADate(String tickerName, Date transactionDate) throws SQLException {
    fetchStockData(tickerName, transactionDate);
    return handler.getStockPriceOnADate(tickerName, transactionDate) != 0;
  }

  public double getStockPriceOnADate(String tickerName, Date transactionDate) throws SQLException {
    fetchStockData(tickerName, transactionDate);
    return handler.getStockPriceOnADate(tickerName, transactionDate);
  }

  public void fetchStockData(String tickerName, Date transactionDate) throws SQLException {
    Date maximumDate = handler.getMaxAvailableStockDataDate(tickerName);

    if(maximumDate == null || maximumDate.compareTo(transactionDate) < 0) {
      getAllStockDataFromAPI(tickerName);
    }
  }

  private void getAllStockDataFromAPI(String tickerName) throws SQLException {
    List<StockDataModel> data = new AlphaVantageModel().getTimeSeriesDataForAStock(tickerName);
    handler.insertStockData(tickerName, data);
  }


  public void addTransaction(int portfolioId, String stockName, String transactionType, int quantity,
                             double price, Date transactionDate, double commission) throws SQLException {
    handler.addTransaction(portfolioId, stockName, transactionType, quantity, price, transactionDate, commission);
  }

  public int getPortfolioId(int userId, String portfolioName) throws SQLException {
    return handler.getPortfolioId(userId, portfolioName);
  }

  public int addStrategy(String strategyName, int portfolioId, Date startDate,
                         Date endDate, double amount, double commission, int frequency) throws SQLException {
    return handler.addStrategy(strategyName, portfolioId, startDate, endDate, amount, commission, frequency);
  }

  public void addStrategyElement(int strategyId, String stockName, Double weight) throws SQLException {
    handler.addStrategyElement(strategyId, stockName, weight);
  }

  public void runStrategy(int strategyId) throws SQLException {
    handler.runStrategy(strategyId);
  }

  public void deletePortfolio(int userId, String portfolioName) throws SQLException {
    handler.deletePortfolio(userId, portfolioName);
  }

  public List<List<String>> getPortfolioComposition(int userId, String portfolioName, Date date) throws SQLException {
    return handler.getPortfolioComposition(userId, portfolioName, date);
  }

  public double getPortfolioValueByDate(int userId, String portfolioName, Date date) throws SQLException {
    return handler.getPortfolioValueByDate(userId, portfolioName, date);
  }

  public double getCostBasis(int userId, String portfolioName, Date date) throws SQLException {
    return handler.getCostBasis(userId, portfolioName, date);
  }

  public ChartPlot getPerformanceForPortfolio(Date startDate, Date endDate, String portfolioName) throws SQLException {
    DateRange dateRange = DateRangeCalculatorUtil.getDateRange(startDate, endDate);
    Map<Date, Double> datePriceMap = getPricesForDates(portfolioName, dateRange.getDateList(), true);
    return ChartPlotCalculatorUtil.buildChartPlot(datePriceMap,
            dateRange.getDateRangeType(), 50);
  }

  private Map<Date, Double> getPricesForDates(String portfolioName, List<Date> dates, boolean lookBack) throws SQLException {
    Map<Date, Double> datePricesMap = new LinkedHashMap<>();
    for (Date date : dates) {
      double price = getPortfolioValueByDate(user.getUserId(), portfolioName, date);
      if (price == 0 && lookBack) {
        date = getPreviousWorkingDateFromPortfolioData(portfolioName, date);
        price = getPortfolioValueByDate(user.getUserId(), portfolioName, date);
      }
      datePricesMap.put(date, price);
    }
    return datePricesMap;
  }

  protected Date getPreviousWorkingDateFromPortfolioData(String portfolioName, Date date) throws SQLException {
    if (getPortfolioValueByDate(user.getUserId(), portfolioName, date) == 0) {
      Date currentDate = date;
      int totalLookBack = 10;
      while (getPortfolioValueByDate(user.getUserId(), portfolioName, date) == 0 && totalLookBack != 0) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusDays(1);
        date = Date.from(localDate.atStartOfDay(ZoneId.of("America/New_York")).toInstant());
        totalLookBack--;
      }
      if (totalLookBack == 0) {
        return currentDate;
      }
      return date;
    }
    return date;
  }

  public Map<String, Double> getPortfolioElementsForModify(int userId, String portfolioName) throws SQLException {
    return handler.getPortfolioElementsForModify(userId, portfolioName);
  }

  public ChartPlot getPerformanceForStock(String stockName, Date startDateGraph, Date endDateGraph)
          throws SQLException {
    DateRange dateRange = DateRangeCalculatorUtil.getDateRange(startDateGraph, endDateGraph);
    Map<Date, Double> stockPricesMap = handler.getStockPrices(stockName, startDateGraph, endDateGraph);
    if(stockPricesMap.size() == 0) {
      getAllStockDataFromAPI(stockName);
      stockPricesMap = handler.getStockPrices(stockName, startDateGraph, endDateGraph);
    }
    Map<Date, Double> datePriceMap = getStockPricesForDates(stockPricesMap, stockName,
            dateRange.getDateList(), true);
    return ChartPlotCalculatorUtil.buildChartPlot(datePriceMap,
            dateRange.getDateRangeType(), 50);
  }

  private Map<Date, Double> getStockPricesForDates(Map<Date, Double> stockPricesMap,
                                                   String stockName, List<Date> dateList,
                                                   boolean lookBack) throws SQLException {
    Map<Date, Double> datePricesMap = new LinkedHashMap<>();
    for (Date date : dateList) {
      double price = stockPricesMap.getOrDefault(date, 0.0);
      if (price == 0 && lookBack) {
        date = getPreviousWorkingDateFromStockData(stockPricesMap, stockName, date);
        price = stockPricesMap.getOrDefault(date, 0.0);
      }
      datePricesMap.put(date, price);
    }
    return datePricesMap;
  }

  protected Date getPreviousWorkingDateFromStockData(Map<Date, Double> stockPricesMap,
                                                     String stockName, Date date) throws SQLException {
    if (stockPricesMap.getOrDefault(date, 0.0) == 0) {
      Date currentDate = date;
      int totalLookBack = 10;
      while (stockPricesMap.getOrDefault(date, 0.0) == 0 && totalLookBack != 0) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusDays(1);
        date = Date.from(localDate.atStartOfDay(ZoneId.of("America/New_York")).toInstant());
        totalLookBack--;
      }
      if (totalLookBack == 0) {
        return currentDate;
      }
      return date;
    }
    return date;
  }

  public String[] getProfileDetails(int userId) throws SQLException {
    return handler.getProfileDetails(userId);
  }

  public void updateProfile(int userId, String nickName, String phoneNumber, String password) throws SQLException {
    handler.updateProfile(userId, nickName, phoneNumber, password);
  }

  public List<String> getAllBrokers() throws SQLException {
    return handler.getAllBrokers();
  }

  public void addBroker(int userId, String broker) throws SQLException {
    handler.addBroker(userId, broker);
  }

  public Map<String, Double> getBrokerCommissions(int userId) throws SQLException {
    return handler.getBrokerCommissions(userId);
  }
}
