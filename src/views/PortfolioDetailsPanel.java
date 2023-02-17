package views;


import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Features;
import utils.ChartPlot;

/**
 * Panel class to show the portfolio details panel. It extends JPanel ad all its features.
 */
public class PortfolioDetailsPanel extends JPanel {
  private String portfolioName;
  private JButton bShowGraph;
  private JButton bShowCostBasis;
  private JButton bGetValueOnADate;
  private JButton bShowComposition;
  private JButton bBack;
  private JLabel lPortfolioName;
  private PortfolioDetailsCard portfolioDetailsCard;

  /**
   * Public constructor to initialise portfolio details.
   */
  public PortfolioDetailsPanel() {
    this.portfolioName = "";
    initializeView();
  }

  protected void setPortfolioName(String portfolioName) {
    this.portfolioName = portfolioName;
    lPortfolioName.setText(this.portfolioName);
  }


  private void initializeView() {
    lPortfolioName = new JLabel(this.portfolioName, SwingConstants.CENTER);
    lPortfolioName.setAlignmentX(Component.CENTER_ALIGNMENT);
    bShowGraph = new JButton("Show Performance");
    bShowCostBasis = new JButton("Show Cost Basis");
    bGetValueOnADate = new JButton("Get Value on a Date");
    bShowComposition = new JButton("Show Composition");
    bBack = new JButton("Back");

    JPanel secondPanel = new JPanel();
    secondPanel.add(bShowComposition);
    secondPanel.add(bShowGraph);
    secondPanel.add(bShowCostBasis);
    secondPanel.add(bGetValueOnADate);
    secondPanel.add(bBack);

    JPanel pHeader = new JPanel();
    pHeader.setLayout(new BoxLayout(pHeader, BoxLayout.Y_AXIS));
    pHeader.add(lPortfolioName);
    pHeader.add(secondPanel);

    portfolioDetailsCard = new PortfolioDetailsCard();

    this.setLayout(new BorderLayout());
    this.add(pHeader, BorderLayout.PAGE_START);
    this.add(portfolioDetailsCard, BorderLayout.CENTER);
  }

  protected void addFeatures(Features features) {
    bBack.addActionListener((e) -> features.goToMainMenu());
    bGetValueOnADate.addActionListener((e) -> features.getValueOnADate(portfolioName));
    bShowCostBasis.addActionListener((e) -> features.getCostBasis(portfolioName));
    bShowComposition.addActionListener((e) -> features.showComposition(portfolioName));
    bShowGraph.addActionListener((e) -> features.showGraph(portfolioName));
    portfolioDetailsCard.addFeatures(features);
  }

  protected void showValueOnADate(Date date, double value) {
    portfolioDetailsCard.showValueOnADate(date, value);
  }

  protected void showComposition(List<List<String>> composition) {
    portfolioDetailsCard.showComposition(composition);
  }

  protected void showCostBasis(Date date, double value) {
    portfolioDetailsCard.showCostBasis(date, value);
  }

  protected void clearPage() {
    portfolioDetailsCard.clearPage();
  }

  protected void showBarChart(ChartPlot chartPlot) {
    portfolioDetailsCard.showBarChart(chartPlot);
  }
}
