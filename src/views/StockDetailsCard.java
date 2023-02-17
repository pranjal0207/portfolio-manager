package views;

import java.awt.*;

import javax.swing.*;

import controller.Features;
import utils.ChartPlot;

public class StockDetailsCard extends JPanel {
  JLabel lBlank;
  CustomBarChartPanel cbPerformance;
  private Features features;

  public StockDetailsCard() {
    initializeViews();
  }

  private void initializeViews() {
    this.setLayout(new CardLayout());

    lBlank = new JLabel("Select a stock to view its performance", SwingConstants.CENTER);
    cbPerformance = new CustomBarChartPanel(Color.PINK, 200, 50,
            2, 3);


    this.add(lBlank, "Blank");
    this.add(cbPerformance, "Performance");

    switchLayoutHelper("Blank");
  }

  protected void addFeatures(Features features) {
    this.features = features;
  }

  private void switchLayoutHelper(String newLayout) {
    ((CardLayout) this.getLayout()).show(this, newLayout);
    if (features != null) {
      this.features.packLayout();
    }
  }

  protected void showProcessing() {
    lBlank.setText("Processing...Please wait");
    lBlank.paintImmediately(lBlank.getVisibleRect());
  }

  protected void clearProcessing() {
    lBlank.setText("Select a stock to view its performance");
    lBlank.paintImmediately(lBlank.getVisibleRect());
  }

  protected void showBarChart(ChartPlot chartPlot) {
    cbPerformance.setData(chartPlot);
    switchLayoutHelper("Performance");
  }

  protected void clearPage() {
    switchLayoutHelper("Blank");
  }
}
