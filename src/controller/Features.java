package controller;

import java.util.List;
import java.util.Map;

/**
 * This interface represents all the features which are there in the stock system.
 * All the features are implemented in the GUI and used by controller.
 */
public interface Features {
  /**
   * Feature to exit the program.
   */
  void exitProgram();

  /**
   * Feature to login to program.
   */
  void login(String userName, String password);

  /**
   * Feature to create a new user.
   */
  void signUp(String userName, String password, List<String> brokers);

  /**
   * Feature to logout a user.
   */
  void logout();

  /**
   * Feature to analyze a portfolio.
   * @param portfolioName name of portfolio to analyze.
   */
  void analyzePortfolio(String portfolioName);

  /**
   * Feature to create a portfolio.
   */
  void requestCreatePortfolio();

  /**
   * Feature to go to the main menu.
   */
  void goToMainMenu();

  /**
   * Feature to get value of portfolio on a date.
   * @param portfolioName portfolio whose value needs to be calculated.
   */
  void getValueOnADate(String portfolioName);

  /**
   * Feature to get cost basis of a portfolio.
   * @param portfolioName portfolio whose cost basis needs to be calculated.
   */
  void getCostBasis(String portfolioName);

  /**
   * Feature to get composition of portfolio.
   * @param portfolioName portfolio whose composition needs to be calculated.
   */
  void showComposition(String portfolioName);

  /**
   * Feature to modify a portfolio.
   * @param portfolioName name of portfolio to modify.
   */
  void requestModifyPortfolio(String portfolioName);

  /**
   * Feature to request more stocks and their inputs.
   */
  void requestMoreStockInput();

  /**
   * Feature to add another strategy to portfolio.
   * @param portfolioName name of portfolio to add strategy to.
   */
  void requestAddStrategy(String portfolioName);

  /**
   * Feature to request more stocks and their inputs for strategy.
   */
  void requestMoreStockInputForStrategy();

  /**
   * Feature to pack a layout for viewing.
   */
  void packLayout();

  /**
   * Feature to create a portfolio without a strategy.
   */
  void requestCreateFlexiblePortfolioWithoutStrategy(Map<String, Double> brokerCommissions);

  /**
   * Show the performance graph of portfolio.
   * @param portfolioName portfolio whose performance graph needs to be calculated.
   */
  void showGraph(String portfolioName);

  /**
   *  Save a strategy to portfolio.
   * @param portfolioName portfolio where strategy needs to be saved.
   * @param strategyType type of strategy.
   * @param strategyName name of strategy.
   * @param amountText amount to be invested.
   * @param commission commission per transaction.
   * @param tStartDateText start date of strategy.
   * @param tEndDateText end date of strategy.
   * @param tFrequencyCountText investment frequency.
   * @param stockNames name of stocks to be invested.
   * @param weights weights of each stock.
   */
  void saveStrategy(String portfolioName, String strategyType, String strategyName,
                    String amountText, String commission,
                    String tStartDateText, String tEndDateText, String tFrequencyCountText,
                    List<String> stockNames, List<String> weights);

  /**
   * Method to create flexible portfolio without strategy.
   * @param portfolioName name of portfolio to be created.
   * @param stockNames stocks to be created.
   * @param quantities quantity of each stocks.
   * @param commissions commissions for each stock.
   * @param dates dates on which stocks are bought.
   */
  void createFlexiblePortfolioWithoutStrategy(String portfolioName,
                                              List<String> stockNames, List<String> quantities,
                                              List<String> commissions, List<String> dates);

  /**
   * Method to modify a portfolio.
   * @param portfolioName name of portfolio to be modified.
   * @param stockTicker stock ticker to be modified.
   * @param quantity quantity to modify.
   * @param commission commission for transaction.
   * @param buySell buy or sell transaction.
   * @param date date of transaction.
   */
  void modifyPortfolio(String portfolioName, String stockTicker, String quantity, String commission,
                       String buySell, String date);

  void deletePortfolio(String portfolioName);

  void showStocksGraph(String stockName, String startDate, String endDate);

  void analyzeStocks();

  void requestUpdateProfile();

  void updateProfile(String nickName, String phoneNumber, String password);

  void requestBrokersSelection();

  void closeChooseBrokerPanel(List<String> selectedItems);
}
