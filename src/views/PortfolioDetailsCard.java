package views;


import java.awt.CardLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.Features;
import utils.ChartPlot;

/**
 * Panel class to show the portfolio details panel. It extends JPanel ad all its features.
 */
public class PortfolioDetailsCard extends JPanel {
  JLabel lBlank;
  JLabel lValueOfPortfolio;
  JLabel lCostBasis;
  JTable tComposition;

  CustomBarChartPanel cbPerformance;

  private Features features;

  /**
   * Public constructor to initialise a portfolio details panel.
   */
  public PortfolioDetailsCard() {
    initializeViews();
  }

  private void initializeViews() {
    this.setLayout(new CardLayout());

    lBlank = new JLabel("Select an operation to get started", SwingConstants.CENTER);
    lValueOfPortfolio = new JLabel("Portfolio Value", SwingConstants.CENTER);
    lCostBasis = new JLabel("Cost Basis", SwingConstants.CENTER);
    tComposition = CompositionPanelCreator.getCompositionPanelTable(new ArrayList<>());
    cbPerformance = new CustomBarChartPanel(Color.PINK, 200, 50,
            2, 3);

    this.add(lBlank, "Blank");
    this.add(lValueOfPortfolio, "Portfolio Value");
    this.add(lCostBasis, "Cost Basis");
    this.add(tComposition, "Composition");
    this.add(cbPerformance, "Performance");

    switchLayoutHelper("Blank");
  }

  protected void addFeatures(Features features) {
    this.features = features;
  }

  void showValueOnADate(Date date, double value) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    lValueOfPortfolio.setText(String.format("Value of the portfolio as of %s is %f",
            format.format(date), value));
    switchLayoutHelper("Portfolio Value");
  }

  void showCostBasis(Date date, double value) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    lCostBasis.setText(String.format("Cost Basis of the portfolio as of %s is %f",
            format.format(date), value));
    switchLayoutHelper("Cost Basis");
  }

  private void switchLayoutHelper(String newLayout) {
    ((CardLayout) this.getLayout()).show(this, newLayout);
    if (features != null) {
      this.features.packLayout();
    }
  }

  protected void showComposition(List<List<String>> composition) {
    this.remove(tComposition);
    tComposition = CompositionPanelCreator.getCompositionPanelTable(composition);
    this.add(tComposition, "Composition");
    switchLayoutHelper("Composition");
  }

  protected void clearPage() {
    switchLayoutHelper("Blank");
  }

  protected void showBarChart(ChartPlot chartPlot) {
    cbPerformance.setData(chartPlot);
    switchLayoutHelper("Performance");
  }
}
