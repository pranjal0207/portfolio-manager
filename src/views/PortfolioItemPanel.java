package views;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Features;

/**
 *  Panel class to show the portfolio item panel. It extends JPanel ad all its features.
 */
public class PortfolioItemPanel extends JPanel {
  private final String portfolioName;
  private JButton bAnalyze;
  private JButton bModify;
  private JButton bAddStrategy;
  private JButton bDelete;

  /**
   * Public constructor to initialise a portfolio item panel.
   * @param portfolioName portfolio name as string only.
   */
  public PortfolioItemPanel(String portfolioName) {
    this.portfolioName = portfolioName;
    initializeView();
  }

  private void initializeView() {
    JLabel lPortfolioName = new JLabel(portfolioName);
    lPortfolioName.setMaximumSize(new Dimension(150, 20));
    lPortfolioName.setMinimumSize(new Dimension(150, 20));
    lPortfolioName.setPreferredSize(new Dimension(150, 20));
    bAnalyze = new JButton("Analyze");
    bModify = new JButton("Modify");
    bAddStrategy = new JButton("Add Strategy");
    bDelete = new JButton("Delete");

    this.add(lPortfolioName);
    this.add(bAnalyze);
    this.add(bAddStrategy);
    this.add(bModify);
    this.add(bDelete);
  }

  protected void addFeatures(Features features) {
    bAnalyze.addActionListener((e) -> features.analyzePortfolio(portfolioName));
    bModify.addActionListener((e) -> features.requestModifyPortfolio(portfolioName));
    bAddStrategy.addActionListener((e) -> features.requestAddStrategy(portfolioName));
    bDelete.addActionListener((e) -> features.deletePortfolio(portfolioName));
  }
}
