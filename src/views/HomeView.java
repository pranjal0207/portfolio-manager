package views;


import com.sun.jdi.DoubleType;

import java.awt.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Features;
import models.Strategy;
import utils.ChartPlot;

/**
 * HomeView class to show the view. It implements
 * GUIView and extends the JFrame class.
 */
public class HomeView extends JFrame implements GUIView {
  private MainMenuPanel pMainMenuPanel;
  private LoginPanel pLogin;
  private JPanel pCards;
  private PortfolioDetailsPanel pPortfolioDetailsPanel;
  private StockDetailsPanel pStockDetailsPanel;
  private Features features;
  private CreatePortfolioWithoutStrategyPanel createPortfolioWithoutStrategyPanel;
  private JDialog createPortfolioWithoutStrategyDialog;
  private AddStrategyPanel addStrategyPanel;
  private JDialog addStrategyDialog;
  private JDialog modifyPortfolioDialog;
  private ProfilePanel pProfilePanel;

  ModifyPortfolioPanel modifyPortfolioPanel;

  private JDialog processingDialog;

  /**
   * Public constructor to initialize home view.
   */
  public HomeView() {
    initializeLayout();
    initializeViews();
  }

  private void initializeViews() {
    pMainMenuPanel = new MainMenuPanel();
    pPortfolioDetailsPanel = new PortfolioDetailsPanel();
    pStockDetailsPanel = new StockDetailsPanel();
    pLogin = new LoginPanel(this);
    pCards = new JPanel(new CardLayout());
    pProfilePanel = new ProfilePanel();
    pCards.add(pLogin, "Login");
    pCards.add(pMainMenuPanel, "Main Menu");
    pCards.add(pPortfolioDetailsPanel, "Portfolio Details");
    pCards.add(pStockDetailsPanel, "Stock Details");
    pCards.add(pProfilePanel, "Update Profile");
    this.getContentPane().add(pCards);
    ((CardLayout) pCards.getLayout()).show(pCards, "Login");
    setVisible(true);
    this.pack();
  }

  private void initializeLayout() {
    setLocation(200, 200);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel processingPanel = new JPanel();
    processingPanel.setBackground(Color.yellow);
    JLabel lProcessing = new JLabel("Processing...Please wait");
    processingPanel.add(lProcessing);
    processingDialog = new JDialog(this,
            "Processing");
    processingDialog.setLocation(500, 300);
    processingDialog.setSize(500, 500);
    processingDialog.setUndecorated(true);
    processingDialog.setContentPane(processingPanel);
    processingDialog.pack();
    processingDialog.setResizable(false);
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
    pLogin.addFeatures(features);
    pMainMenuPanel.addFeatures(features);
    pPortfolioDetailsPanel.addFeatures(features);
    pStockDetailsPanel.addFeatures(features);
    pProfilePanel.addFeatures(features);
  }

  @Override
  public void switchLayout(String newLayout) {
    switchLayoutHelper(newLayout);
  }

  @Override
  public void showCreatePortfolioProcessing() {
    createPortfolioWithoutStrategyPanel.showProcessing();
  }

  @Override
  public void clearCreatePortfolioProcessing() {
    createPortfolioWithoutStrategyPanel.clearProcessing();
  }

  @Override
  public void showAddStrategyProcessing() {
    addStrategyPanel.showProcessing();
  }

  @Override
  public void clearAddStrategyProcessing() {
    addStrategyPanel.clearProcessing();
  }

  @Override
  public void showModifyProcessing() {
    modifyPortfolioPanel.showProcessing();
  }

  @Override
  public void clearModifyProcessing() {
    modifyPortfolioPanel.clearProcessing();
  }

  @Override
  public void showStockGraphProcessing() {
    pStockDetailsPanel.clearPage();
    pStockDetailsPanel.showProcessing();
  }

  @Override
  public void clearStockGraphProcessing() {
    pStockDetailsPanel.clearPage();
    pStockDetailsPanel.clearProcessing();
  }

  @Override
  public void packLayout() {
    pack();
  }

  @Override
  public void openMainMenuLayout(String userName, int userID, List<String> portfolios) {
    clearPortfolios();
    pPortfolioDetailsPanel.showBarChart(new ChartPlot());
    pMainMenuPanel.setUserFields(userID, userName);
    pMainMenuPanel.addPortfolios(portfolios);
    switchLayoutHelper("Main Menu");
  }

  private void clearPortfolios() {
    pMainMenuPanel.removeAllPortfolios();
  }

  private void switchLayoutHelper(String newLayout) {
    ((CardLayout) pCards.getLayout()).show(pCards, newLayout);
    features.packLayout();
  }

  @Override
  public void showNoUserFoundPrompt() {
    JOptionPane.showMessageDialog(null,
            "No user found. You might want to create a new user");
  }

  @Override
  public void requestCreatePortfolio(Map<String, Double> brokerCommissions) {
    features.requestCreateFlexiblePortfolioWithoutStrategy(brokerCommissions);
  }

  @Override
  public void requestCreateFlexiblePortfolioWithoutStrategy(List<String> stocks, Map<String, Double> brokerCommissions) {
    createPortfolioWithoutStrategyPanel = new CreatePortfolioWithoutStrategyPanel(stocks, brokerCommissions);
    createPortfolioWithoutStrategyPanel.addFeatures(this.features);
    createPortfolioWithoutStrategyDialog = new JDialog(this,
            "Create Portfolio Manually");
    createPortfolioWithoutStrategyDialog.setLocation(300, 200);
    createPortfolioWithoutStrategyDialog.setSize(500, 500);
    createPortfolioWithoutStrategyDialog.setContentPane(createPortfolioWithoutStrategyPanel);
    createPortfolioWithoutStrategyDialog.pack();
    createPortfolioWithoutStrategyDialog.setResizable(false);
    createPortfolioWithoutStrategyDialog.setVisible(true);
  }

  @Override
  public void setPortfolios(List<String> portfolios) {
    this.pMainMenuPanel.removeAllPortfolios();
    this.pMainMenuPanel.addPortfolios(portfolios);
  }

  @Override
  public void clearLoginPageInputs() {
    this.pLogin.clearInputs();
  }

  @Override
  public void clearPortfolioDetailsPage() {
    this.pPortfolioDetailsPanel.clearPage();
  }

  @Override
  public void showModifyPortfolioSuccessPopup() {
    JOptionPane.showMessageDialog(null,
            "Portfolio modified successfully");
  }

  @Override
  public void showAddStrategySuccessPopup() {
    JOptionPane.showMessageDialog(null,
            "Strategy added successfully");
  }

  @Override
  public void showMessageDialog(String message) {
    JOptionPane.showMessageDialog(null,
            message);
  }

  @Override
  public void openPortfolioDetails(String portfolioName, List<List<String>> composition) {
    pPortfolioDetailsPanel.setPortfolioName(portfolioName);
    pPortfolioDetailsPanel.showBarChart(new ChartPlot());
    pPortfolioDetailsPanel.showComposition(composition);
    switchLayoutHelper("Portfolio Details");
  }

  @Override
  public String getDateInput() {
    DateInputPanel panel = new DateInputPanel();
    int result = JOptionPane.showConfirmDialog(null, panel, "Date",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      return panel.getDate();
    }

    return null;
  }

  @Override
  public String[] getStartEndDateInput() {
    StartEndDateInputPanel panel = new StartEndDateInputPanel();
    int result = JOptionPane.showConfirmDialog(null, panel, "Date",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      return new String[]{panel.getStartDate(), panel.getEndDate()};
    }

    return new String[0];
  }


  @Override
  public void showWrongDateInputPopup() {
    JOptionPane.showMessageDialog(null,
            "Incorrect date input. Please try again.");
  }

  @Override
  public void showValueOnADate(Date date, double value) {
    pPortfolioDetailsPanel.showBarChart(new ChartPlot());
    pPortfolioDetailsPanel.showValueOnADate(date, value);
    pack();
  }

  @Override
  public void showCostBasis(Date date, double value) {
    pPortfolioDetailsPanel.showBarChart(new ChartPlot());
    pPortfolioDetailsPanel.showCostBasis(date, value);
    pack();
  }

  @Override
  public void showComposition(List<List<String>> composition) {
    pPortfolioDetailsPanel.showBarChart(new ChartPlot());
    pPortfolioDetailsPanel.showComposition(composition);
    pack();
  }

  @Override
  public void showIncorrectQuantityPopup() {
    JOptionPane.showMessageDialog(null,
            "Incorrect Quantity input, please try again.");
  }

  @Override
  public void showBarChart(String startDate, String endDate,
                           String portfolioName, ChartPlot chartPlot) {
    pPortfolioDetailsPanel.showBarChart(chartPlot);
    pack();
  }

  @Override
  public void showIncorrectWeightPopup() {
    JOptionPane.showMessageDialog(null,
            "Please make sure stock weightage sums to exactly 100");
  }

  @Override
  public void showIncorrectCommissionPopup() {
    JOptionPane.showMessageDialog(null,
            "Incorrect commission input, please try again.");
  }

  @Override
  public void showIncorrectAmountPopup() {
    JOptionPane.showMessageDialog(null,
            "Incorrect amount input, please try again.");
  }

  @Override
  public void showIncorrectFrequencyPopup() {
    JOptionPane.showMessageDialog(null,
            "Incorrect frequency input, please try again.");
  }

  @Override
  public void requestMoreStockInput() {
    if (createPortfolioWithoutStrategyPanel != null) {
      this.createPortfolioWithoutStrategyPanel.addStock();
      this.createPortfolioWithoutStrategyDialog.pack();
    }
  }

  @Override
  public void requestMoreStockInputForStrategy() {
    this.addStrategyPanel.getAddStrategyComponentPanel().addStock();
    this.addStrategyDialog.pack();
  }

  @Override
  public void closeAddFlexiblePortfolioWithoutStrategyPopup(List<String> portfolios) {
    this.createPortfolioWithoutStrategyDialog.setVisible(false);
    this.pMainMenuPanel.removeAllPortfolios();
    this.pMainMenuPanel.addPortfolios(portfolios);
  }

  @Override
  public void showModifyPortfolioPopup(String portfolioName, Map<String, Double> portfolioElements,
                                       List<String> stocks) {
    modifyPortfolioPanel = new ModifyPortfolioPanel(portfolioName,
            portfolioElements, stocks);
    modifyPortfolioPanel.addFeatures(this.features);
    modifyPortfolioDialog = new JDialog(this,
            "Modify Portfolio", true);
    modifyPortfolioDialog.setLocation(300, 200);
    modifyPortfolioDialog.setSize(500, 500);
    modifyPortfolioDialog.setContentPane(modifyPortfolioPanel);
    modifyPortfolioDialog.pack();
    modifyPortfolioDialog.setResizable(false);
    modifyPortfolioDialog.setVisible(true);
  }

  @Override
  public void showNoPriceOnDatePopup(String stockName) {
    JOptionPane.showMessageDialog(null,
            String.format("No price found on the given date for stock - %s,"
                    + " please input correct date.", stockName));
  }

  @Override
  public void closeAddStrategyPopup() {
    this.addStrategyDialog.setVisible(false);
  }

  @Override
  public void closeModifyPortfolioPopup() {
    modifyPortfolioDialog.setVisible(false);
  }

  @Override
  public void showProcessingPopup() {
    processingDialog.setVisible(true);
    processingDialog.toFront();
    processingDialog.repaint();
  }

  @Override
  public void closeProcessingPopup() {
    processingDialog.setVisible(false);
  }

  @Override
  public void requestAddStrategy(String portfolioName, List<String> stocks,
                                 List<Strategy> strategyTypes) {
    addStrategyPanel = new AddStrategyPanel(portfolioName, stocks, strategyTypes);
    addStrategyPanel.addFeatures(this.features);
    addStrategyDialog = new JDialog(this,
            "Add Strategy", true);
    addStrategyDialog.setLocation(300, 200);
    addStrategyDialog.setSize(500, 500);
    addStrategyDialog.setContentPane(addStrategyPanel);
    addStrategyDialog.pack();
    addStrategyDialog.setResizable(false);
    addStrategyDialog.setVisible(true);
  }

  @Override
  public void showAnalyzeStocks(List<String> stocks) {
    pStockDetailsPanel.setStocks(stocks);
    switchLayoutHelper("Stock Details");
  }

  @Override
  public void showStockBarChart(ChartPlot chartPlot) {
    pStockDetailsPanel.showGraph(chartPlot);
    pack();
  }

  @Override
  public void requestUpdateProfile(String[] profileDetails) {
    pProfilePanel.setProfile(profileDetails[0], profileDetails[1], profileDetails[2]);
    switchLayoutHelper("Update Profile");
  }

  @Override
  public void clearAnalyzeStocksPage() {
    pStockDetailsPanel.clearPage();
  }

  @Override
  public void requestBrokerSelection() {
    pLogin.requestBrokersSelection();
  }

  @Override
  public void chooseBrokers(List<String> brokers) {
    pLogin.setSelectedBrokers(brokers);
  }

  @Override
  public void setBrokers(List<String> brokers) {
    pLogin.setBrokers(brokers);
  }

}
