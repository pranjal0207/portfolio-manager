package views;

import java.util.Date;
import java.util.List;

import utils.ChartPlot;

/**
 * This interface represents various text based
 * views that can be displayed to user.
 */
public interface TextBasedView {
  /**
   * View to display on initial screen.
   */
  void showWelcomeDialog();

  /**
   * View to display on user creation.
   */
  void showCreateUserDialog();

  /**
   * View to display on showing main menu.
   */
  void showMainMenu();

  /**
   * View to display while taking add portfolio prompt.
   */
  void showAddPortfolioNamePrompt();

  /**
   * View to display on add portfolio options.
   */
  void showAddPortfolioOptions();

  /**
   * View to display on asking total number of stocks
   * to add..
   */
  void showTotalNumberOfStocksToBeAddedPrompt();

  /**
   * View to display on asking stock symbol.
   */
  void showEnterStockTickerPrompt();

  /**
   * View to display on asking quantity.
   */
  void showEnterQuantityPrompt();

  void showEnterCommissionPrompt();

  /**
   * View to display on asking file path.
   */
  void showEnterFilePathPrompt();

  /**
   * View to display on having no portfolio.
   */
  void showNoPortfoliosPrompt();

  /**
   * View to display on welcoming a user
   * with credentials.
   *
   * @param userName name of the user.
   * @param userId   id of the user.
   */
  void welcomeUserPrompt(String userName, int userId);

  /**
   * View to display on entering special characters.
   */
  void noSpecialCharactersAllowedPrompt();

  /**
   * View to display on no price data for a date.
   */
  void showNoDataFoundForDatePrompt();


  /**
   * View to display on successfully saving portfolio.
   *
   * @param path path where portfolio is saved.
   */
  void savePortfolioSuccessMessage(String path);

  /**
   * View to display portfolio value on a date.
   *
   * @param date  date for which value is to be calculated.
   * @param value the chalculated value of portfolio.
   */
  void showPortfolioValueForDate(Date date, double value);

  /**
   * View to display on giving option to choose one portfolio.
   *
   * @param portfolioModelList list of portfolios to be selected.
   */
  void showSelectPortfolioPrompt(List<String> portfolioModelList);

  /**
   * View to display on showing portfolio details.
   *
   * @param portfolioModelName name of the portfolio.
   * @param portfolioElement   details of all the portfolio elements.
   */
  void showPortfolioDetails(String portfolioModelName, List<List<String>> portfolioElement);

  /**
   * View to display on entering wrong input.
   */
  void showWrongInputDialog();

  /**
   * View to display on entering existing portfolio name.
   */
  void showPortfolioNameAlreadyExistsPrompt();

  /**
   * View to display if data non-existent for one stock or other.
   */
  void showNoDataAvailableForOneOrMoreStockForTheDataEntered();

  /**
   * View to display on asking userId.
   */
  void showEnterUserIDDialog();

  /**
   * View to display on asking date.
   */
  void showEnterDatePrompt();

  /**
   * View to display on finding no user.
   */
  void showNoUserFoundPrompt();

  /**
   * View to display on initialization failing.
   */
  void showInitializationFailedPrompt();

  /**
   * View to display on successfully saving a portfolio.
   *
   * @param portfolioName name of portfolio saved successfully.
   */
  void showPortfolioAddSuccessMessage(String portfolioName);

  /**
   * View to display on aborting the system.
   */
  void showAbortingPrompt();

  /**
   * View to display on successfully logging.
   */
  void showLoginSuccessPrompt();

  /**
   * View to display on asking stock symbol.
   */
  void showUserCreateSuccessPrompt();

  /**
   * View to display on error while storing to database.
   */
  void showDBStoreErrorMessage();

  /**
   * View to display on error while saving portfolio.
   *
   * @param portfolio portfolio name which failed to store.
   */
  void showSavePortfolioErrorMessage(String portfolio);

  /**
   * View to display on error while uploading xml.
   */
  void showUploadPortfolioByXMLError();

  /**
   * View to display on appending another
   * output string.
   *
   * @param toPrint String to be printed to output.
   */
  void printAndAddToAppendable(String toPrint);

  /**
   * View to show different add portfolio type options.
   */
  void showDifferentAddPortfolioTypeOptions();

  /**
   * View to show portfolio modify success message.
   */
  void showPortfolioModifySuccessMessage();

  /**
   * View to show cost basis of portfolio.
   * @param date date on which cost basis needs to be shown.
   * @param value value of cost basis.
   */
  void showCostBasisForPortfolio(Date date, double value);

  /**
   * View to show the start range.
   */
  void showRangeStart();

  /**
   * View to show the end range.
   */
  void showRangeEnd();

  /**
   * View to show future date not allowed.
   */
  void showDateCannotBeInFutureDialog();

  /**
   * View to show invalid negative quantity transaction.
   */
  void showInvalidTransactionAsQuantityNegative();

  /**
   * View to show file upload violates constraints.
   */
  void showInputFileViolatesPortfolioConstraints();

  /**
   * View to show end date cannot be before start date.
   */
  void showEndDateCannotBeBeforeStartDate();

  /**
   * View to show the bar chart.
   * @param startDate start Date of bar chart.
   * @param endDate end Date of bar chart.
   * @param portfolioName portfolio name for which chart is made.
   * @param chartPlot chart plot object with all plotting details.
   */
  void showBarChart(String startDate, String endDate, String portfolioName, ChartPlot chartPlot);
}
