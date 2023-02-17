package views;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Features;

/**
 *  Panel class to show the main menu panel. It extends JPanel ad all its features.
 */
public class MainMenuPanel extends JPanel {
  private JButton bLogout;
  private JButton bAnalyzeStocks;
  private JButton bUpdateProfile;
  private JLabel lWelcome;
  private JPanel pAction;
  private JButton bCreate;
  private String userName;
  private Features features;
  private int userID;

  /**
   * Publi constructor to initialise main menu panel.
   */
  public MainMenuPanel() {
    this.userName = "";
    this.userID = 0;
    initializeView();
  }

  private void initializeView() {
    this.setLayout(new GridLayout(0, 1));
    lWelcome = new JLabel("", SwingConstants.CENTER);
    bLogout = new JButton("Logout");
    bAnalyzeStocks = new JButton("Analyze Stocks");

    bCreate = new JButton("Create Portfolio");
    bUpdateProfile = new JButton("Update Profile");

    pAction = new JPanel();
    pAction.setLayout(new FlowLayout());
    pAction.add(bUpdateProfile);
    pAction.add(bCreate);
    pAction.add(bAnalyzeStocks);
    pAction.add(bLogout);

    this.add(lWelcome);
    this.add(pAction);
  }

  protected void setUserFields(int userID, String userName) {
    this.userID = userID;
    this.userName = userName;
    lWelcome.setText(String.format("Welcome %s", userName));
  }

  protected void addPortfolios(List<String> portfolios) {
    for (String portfolio : portfolios) {
      addPortfolioItemHelper(portfolio);
    }
    this.add(pAction);
    this.validate();
    this.features.packLayout();
  }

  protected void addPortfolioItem(String portfolioName) {
    addPortfolioItemHelper(portfolioName);
  }

  private void addPortfolioItemHelper(String portfolioName) {
    PortfolioItemPanel portfolioItemPanel = new PortfolioItemPanel(portfolioName);
    portfolioItemPanel.addFeatures(this.features);
    this.add(portfolioItemPanel);
  }

  protected void addFeatures(Features features) {
    this.features = features;
    bLogout.addActionListener((e) -> features.logout());
    bCreate.addActionListener((e) -> features.requestCreatePortfolio());
    bAnalyzeStocks.addActionListener((e) -> features.analyzeStocks());
    bUpdateProfile.addActionListener((e) -> features.requestUpdateProfile());
  }

  protected void removeAllPortfolios() {
    this.removeAll();
    this.add(lWelcome);
    this.add(pAction);
    this.validate();
    this.features.packLayout();
  }
}
