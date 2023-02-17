package views;


import java.awt.Component;
import java.util.List;


import javax.swing.*;

import controller.Features;
import models.Strategy;

/**
 * Panel class to show add strategy option.
 * This panel extends the jpanel and all its features.
 */
public class AddStrategyPanel extends JPanel {
  private AddStrategyComponentPanel addStrategyComponentPanel;

  private final String portfolioName;
  private final List<String> stocks;
  private final List<Strategy> strategyTypes;
  private JButton bSaveStrategy;

  private JLabel lProcessing;

  /**
   * Public constructor to initialise a strategy panel.
   * @param portfolioName name of portfolio to be initialised.
   * @param stocks list of stocks to be initialised.
   * @param strategyTypes list of strategies avaialble to be initialised.
   */
  public AddStrategyPanel(String portfolioName,
                          List<String> stocks,
                          List<Strategy> strategyTypes) {
    this.portfolioName = portfolioName;
    this.stocks = stocks;
    this.strategyTypes = strategyTypes;
    initializeViews();
  }

  private void initializeViews() {
    addStrategyComponentPanel = new AddStrategyComponentPanel(stocks, strategyTypes);
    bSaveStrategy = new JButton("Save Strategy");
    bSaveStrategy.setAlignmentX(Component.CENTER_ALIGNMENT);

    lProcessing = new JLabel("<html>Never make forecasts, especially about the future<br><br></html>", JLabel.CENTER);
    lProcessing.setAlignmentX(Component.CENTER_ALIGNMENT);


    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.add(addStrategyComponentPanel);
    this.add(bSaveStrategy);
    this.add(lProcessing);
  }

  protected void addFeatures(Features features) {
    addStrategyComponentPanel.addFeatures(features);
    bSaveStrategy.addActionListener((e) -> features.saveStrategy(this.portfolioName,
            addStrategyComponentPanel.getStrategyType(),
            addStrategyComponentPanel.getStrategyName(),
            addStrategyComponentPanel.getAmount(),
            addStrategyComponentPanel.getCommission(),
            addStrategyComponentPanel.getStartDate(),
            addStrategyComponentPanel.getEndDate(),
            addStrategyComponentPanel.getFrequency(),
            addStrategyComponentPanel.getStockNames(),
            addStrategyComponentPanel.getWeights()));
  }

  protected AddStrategyComponentPanel getAddStrategyComponentPanel() {
    return this.addStrategyComponentPanel;
  }

  protected void showProcessing() {
    lProcessing.setText("Processing...Please wait");
    lProcessing.paintImmediately(lProcessing.getVisibleRect());
  }

  protected void clearProcessing()  {
    lProcessing.setText("<html>Never make forecasts, especially about the future<br><br></html>");
    lProcessing.paintImmediately(lProcessing.getVisibleRect());
  }
}
