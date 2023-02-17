package views;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import utils.ChartPlot;

/**
 * This class implements text based view and all its functionality.
 * It displayed the text to user on the console screen.
 */
public class TextBasedViewImpl implements TextBasedView {

  private final Appendable actionOut;
  private final Appendable menuOut;

  /**
   * Public constructor to initialise a test based view object.
   * @param actionOut Out action print stream.
   * @param menuOut MenuOut print stream.
   */
  public TextBasedViewImpl(PrintStream actionOut, PrintStream menuOut) {
    this.actionOut = actionOut;
    this.menuOut = menuOut;
  }

  @Override
  public void showWelcomeDialog() {
    String welcomeScreen = "Hello!"
            + "\nSelect one of the three options"
            + "\n[1] Create User"
            + "\n[2] Login User"
            + "\n[3] Quit";
    printOnScreen(welcomeScreen);
  }

  @Override
  public void showCreateUserDialog() {
    String createUserDialog = "Enter User Name";
    printOnScreen(createUserDialog);
  }

  @Override
  public void showMainMenu() {
    String mainMenu = "\nSelect one of the below:"
            + "\n[1] Add new portfolio"
            + "\n[2] View a portfolio"
            + "\n[3] Modify a portfolio"
            + "\n[4] Analyze cost basis of a portfolio"
            + "\n[5] Show portfolio performance over time"
            + "\n[6] Determine a portfolio for a date"
            + "\n[7] Download a portfolio"
            + "\n[8] Logout";

    printOnScreen(mainMenu);
  }

  @Override
  public void showAddPortfolioNamePrompt() {
    String addPortfolioPrompt = "Enter portfolio name";
    printOnScreen(addPortfolioPrompt);
  }

  @Override
  public void showAddPortfolioOptions() {
    String addPortfolioOptions = "Select one of the below"
            + "\n[1] Enter data manually"
            + "\n[2] Load data from XML file";
    printOnScreen(addPortfolioOptions);
  }

  @Override
  public void showTotalNumberOfStocksToBeAddedPrompt() {
    String totalNumberOfStocksPrompt = "Enter number of stock entries you would like to make.";
    printOnScreen(totalNumberOfStocksPrompt);
  }

  @Override
  public void showEnterStockTickerPrompt() {
    String enterStockNamePrompt = "Enter ticker symbol";
    printOnScreen(enterStockNamePrompt);
  }

  @Override
  public void showEnterQuantityPrompt() {
    String enterQuantityPrompt = "Enter quantity in whole number";
    printOnScreen(enterQuantityPrompt);
  }

  @Override
  public void showEnterCommissionPrompt() {
    String enterCommissionPrompt = "Enter commission";
    printOnScreen(enterCommissionPrompt);
  }

  @Override
  public void showEnterFilePathPrompt() {
    String fileNamePrompt = "Enter file Name with its extension."
            + "\nPlease ensure file is located in same location as the program.";
    printOnScreen(fileNamePrompt);
  }

  @Override
  public void showNoPortfoliosPrompt() {
    String noPortfolioPrompt = "No portfolios found";
    printAndAddToAppendable(noPortfolioPrompt);
  }

  @Override
  public void welcomeUserPrompt(String userName, int userId) {
    String welcomeUserPrompt = String.format("Welcome %s (userId: %d)",
            userName, userId);
    printOnScreen(welcomeUserPrompt);
  }

  @Override
  public void noSpecialCharactersAllowedPrompt() {
    String noSpecialCharAllowed = "No special character allowed."
            + " Please input only alphanumeric values and try again.";
    printAndAddToAppendable(noSpecialCharAllowed);
  }

  @Override
  public void showNoDataFoundForDatePrompt() {
    String noDataFoundPrompt = "No data found for the date."
            + " Please input a valid date on which the stock was actually traded";
    printAndAddToAppendable(noDataFoundPrompt);
  }

  @Override
  public void savePortfolioSuccessMessage(String path) {
    String savePortfolioSuccessMessage = "Saved portfolio at path : " + path;
    printAndAddToAppendable(savePortfolioSuccessMessage);
  }

  @Override
  public void showPortfolioValueForDate(Date date, double value) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String portfolioValueForDate = "Portfolio value for "
            + df.format(date)
            + " as of close is "
            + value;

    printAndAddToAppendable(portfolioValueForDate);
  }

  @Override
  public void showSelectPortfolioPrompt(List<String> portfolios) {
    StringBuilder selectPortfolioPrompt = new StringBuilder("Select a portfolio using Id no."
            + " from below:");

    for (int i = 0; i < portfolios.size(); i++) {
      selectPortfolioPrompt
              .append("\n")
              .append(i + 1)
              .append(". ")
              .append(portfolios.get(i));
    }

    printOnScreen(selectPortfolioPrompt.toString());
  }

  @Override
  public void showPortfolioDetails(String portfolioModelName, List<List<String>> portfolioElement) {
    StringBuilder builder = new StringBuilder("Portfolio Details:")
            .append("\n")
            .append("Portfolio Name: ")
            .append(portfolioModelName)
            .append("\n");
    builder.append(String.format("%-30s%-30s%-30s%-30s%-30s\n", "Ticker Symbol Name",
            "Stock Name", "Total Quantity", "Average Price", "Latest transaction Date"));
    for (List<String> entry :
            portfolioElement) {
      builder.append(String.format("%-30s%-30s%-30s%-30s%-30s\n",
              entry.get(0),
              entry.get(1),
              entry.get(2),
              entry.get(3),
              entry.get(4)));
    }

    printAndAddToAppendable(builder.toString());
    printAndAddToAppendable("\n");
  }

  @Override
  public void showWrongInputDialog() {
    printAndAddToAppendable("Wrong input entered, try again!");
  }

  @Override
  public void showPortfolioNameAlreadyExistsPrompt() {
    String portfolioNameAlreadyExistsPrompt = "Portfolio Name already exists."
            + " Please enter a different portfolio name.";
    printAndAddToAppendable(portfolioNameAlreadyExistsPrompt);
  }

  @Override
  public void showNoDataAvailableForOneOrMoreStockForTheDataEntered() {
    String noDataAvailable = "No data available for one or more stocks for the date entered."
            + " Please try again.";
    printAndAddToAppendable(noDataAvailable);
  }

  @Override
  public void showEnterUserIDDialog() {
    printOnScreen("Enter userId to login to");
  }

  @Override
  public void showEnterDatePrompt() {
    String enterDatePrompt = "Enter date (YYYY-MM-dd format)";
    printOnScreen(enterDatePrompt);
  }

  @Override
  public void showNoUserFoundPrompt() {
    String noUserFoundPrompt = "No user found!"
            + "\nYou might want to create a new user.";
    printAndAddToAppendable(noUserFoundPrompt);
  }

  @Override
  public void showInitializationFailedPrompt() {
    String initializationFailedPrompt = "Failed to initialize the program."
            + "\nYou might want to check the resources and fix any incorrectly formatted files."
            + "\nQuitting....";
    printAndAddToAppendable(initializationFailedPrompt);
  }

  @Override
  public void showPortfolioAddSuccessMessage(String portfolioName) {
    String portfolioAddSuccessMessage = String.format("%s added successfully", portfolioName);
    printAndAddToAppendable(portfolioAddSuccessMessage);
  }

  @Override
  public void showAbortingPrompt() {
    String abortingPrompt = "No price information found for the date. Aborting operation"
            + "\nPlease provide correct information this time";
    printAndAddToAppendable(abortingPrompt);
  }

  @Override
  public void showLoginSuccessPrompt() {
    String loginSuccessFull = "Log In Success";
    printAndAddToAppendable(loginSuccessFull);
  }

  @Override
  public void showUserCreateSuccessPrompt() {
    String userCreateSuccess = "User create success";
    printAndAddToAppendable(userCreateSuccess);
  }

  @Override
  public void showDBStoreErrorMessage() {
    String errorStoringDBMessage = "Entered data could not be stored. Please try again!";
    printAndAddToAppendable(errorStoringDBMessage);
  }

  @Override
  public void showSavePortfolioErrorMessage(String portfolio) {
    String savePortfolioErrorMessage = portfolio + " could not be saved. Please try again!";
    printAndAddToAppendable(savePortfolioErrorMessage);
  }

  @Override
  public void showUploadPortfolioByXMLError() {
    String uploadPortfolioByXMLError = "Error uploading XML file to portfolio. Please try again!";
    printAndAddToAppendable(uploadPortfolioByXMLError);
  }

  @Override
  public void printAndAddToAppendable(String toPrint) {
    try {
      this.actionOut.append(toPrint).append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void showDifferentAddPortfolioTypeOptions() {
    String showDiffPortfolioTypes = "Which type of portfolio do you want to create?"
            + "\n[1] Flexible Portfolio"
            + "\n[2] Inflexible Portfolio";

    printOnScreen(showDiffPortfolioTypes);
  }

  @Override
  public void showPortfolioModifySuccessMessage() {
    String portfolioModifySuccessMessage = "Portfolio modified successfully.";
    printAndAddToAppendable(portfolioModifySuccessMessage);
  }

  @Override
  public void showCostBasisForPortfolio(Date date, double value) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String portfolioCostBasis = "Portfolio cost basis for "
            + df.format(date)
            + " is "
            + value;
    printAndAddToAppendable(portfolioCostBasis);
  }

  @Override
  public void showRangeStart() {
    String rangeStart = "For start of the range";
    printOnScreen(rangeStart);
  }

  @Override
  public void showRangeEnd() {
    String rangeEnd = "For end of the range";
    printOnScreen(rangeEnd);
  }

  @Override
  public void showDateCannotBeInFutureDialog() {
    String dateCannotBeInFuture = "Date cannot be in future. Please try again";
    printAndAddToAppendable(dateCannotBeInFuture);
  }

  @Override
  public void showInvalidTransactionAsQuantityNegative() {
    String invalidTransactionAsQuantityNegative =
            "Invalid input as resultant quantity for the stock turns out to be negative";
    printAndAddToAppendable(invalidTransactionAsQuantityNegative);
  }

  @Override
  public void showInputFileViolatesPortfolioConstraints() {
    String inputFileViolatesPortfolioConstraints = "Input file violates portfolio constraints."
            + "Please check all the inputs and try again.";
    printAndAddToAppendable(inputFileViolatesPortfolioConstraints);
  }

  @Override
  public void showEndDateCannotBeBeforeStartDate() {
    String endDateCannotBeBeforeStartDate = "End date cannot be before start date.";
    printAndAddToAppendable(endDateCannotBeBeforeStartDate);
  }

  @Override
  public void showBarChart(String startDate, String endDate,
                           String portfolioName, ChartPlot chartPlot) {
    StringBuilder builder = new StringBuilder("Performance of portfolio ")
            .append(portfolioName)
            .append(" from ")
            .append(startDate)
            .append(" to ")
            .append(endDate)
            .append("\n");

    for (Map.Entry<String, Integer> plots : chartPlot.getPlots().entrySet()) {
      String plotBuilder = "*".repeat(Math.max(0, plots.getValue()));
      builder.append(String.format("%-10s: %s\n", plots.getKey(),
              plotBuilder));
    }

    builder.append("Relative scale: First * = ")
            .append(chartPlot.getStart())
            .append(" and following * = ")
            .append(chartPlot.getScale())
            .append("\n");

    printAndAddToAppendable(builder.toString());
  }

  private void printOnScreen(String toPrint) {
    try {
      this.menuOut.append(toPrint).append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
