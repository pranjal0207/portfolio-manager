package views;


import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Features;

/**
 * Panel class to show Create Portfolio Without Strategy panel.
 * This panel extends the jpanel and all its features.
 */
public class CreatePortfolioWithoutStrategyPanel extends JPanel {
  private JTextField tPortfolioName;
  private JButton bAddStock;
  private JButton bCreate;
  private JLabel lProcessing;
  private JPanel pStockList;
  private final List<String> stocks;
  private List<StockItemPanel> stockItemPanels;
  private Map<String, Double> brokerCommissions;

  /**
   * Constructor to initialise the panel.
   * @param stocks list of stocks available.
   */
  public CreatePortfolioWithoutStrategyPanel(List<String> stocks, Map<String, Double> brokerCommissions) {
    this.stocks = stocks;
    this.brokerCommissions = brokerCommissions;
    initializeViews();
  }

  private void initializeViews() {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    stockItemPanels = new ArrayList<>();
    JLabel lPortfolioName = new JLabel("Portfolio Name", SwingConstants.CENTER);
    lPortfolioName.setAlignmentX(Component.CENTER_ALIGNMENT);

    tPortfolioName = new JTextField();

    bAddStock = new JButton("Add another Stock");
    bAddStock.setAlignmentX(Component.CENTER_ALIGNMENT);

    bCreate = new JButton("Create Portfolio");
    bCreate.setAlignmentX(Component.CENTER_ALIGNMENT);

    lProcessing = new JLabel("<html>An investor without investment objectives is a traveler without destination<br><br></html>", JLabel.CENTER);
    lProcessing.setAlignmentX(Component.CENTER_ALIGNMENT);

    pStockList = new JPanel();
    pStockList.setLayout(new GridLayout(0, 1));
    StockItemPanel stockItemPanel = new StockItemPanel(stocks, brokerCommissions);
    pStockList.add(stockItemPanel);
    stockItemPanels.add(stockItemPanel);

    this.add(lPortfolioName);
    this.add(tPortfolioName);
    this.add(pStockList);
    this.add(bAddStock);
    this.add(bCreate);
    this.add(lProcessing);
  }

  protected void addFeatures(Features features) {
    bAddStock.addActionListener((e) -> features.requestMoreStockInput());
    bCreate.addActionListener((e) -> features
            .createFlexiblePortfolioWithoutStrategy(tPortfolioName.getText(),
            getStockNames(), getQuantities(), getCommissions(), getDates()));
  }

  protected void addStock() {
    StockItemPanel stockItemPanel = new StockItemPanel(this.stocks, brokerCommissions);
    this.pStockList.add(stockItemPanel);
    this.stockItemPanels.add(stockItemPanel);
    this.pStockList.validate();
  }

  protected void showProcessing() {
    lProcessing.setText("Processing...Please wait");
    lProcessing.paintImmediately(lProcessing.getVisibleRect());
  }

  protected void clearProcessing()  {
    lProcessing.setText("<html>An investor without investment objectives is a traveler without destination<br><br></html>");
    lProcessing.paintImmediately(lProcessing.getVisibleRect());
  }

  private List<String> getStockNames() {
    List<String> stockNames = new ArrayList<>();
    for (StockItemPanel stockItemPanel : stockItemPanels) {
      stockNames.add(stockItemPanel.getStockName());
    }

    return stockNames;
  }

  private List<String> getQuantities() {
    List<String> quantities = new ArrayList<>();
    for (StockItemPanel stockItemPanel : stockItemPanels) {
      quantities.add(stockItemPanel.getQuantity());
    }

    return quantities;
  }

  private List<String> getDates() {
    List<String> dates = new ArrayList<>();
    for (StockItemPanel stockItemPanel : stockItemPanels) {
      dates.add(stockItemPanel.getDate());
    }

    return dates;
  }

  private List<String> getCommissions() {
    List<String> commissions = new ArrayList<>();
    for (StockItemPanel stockItemPanel : stockItemPanels) {
      commissions.add(stockItemPanel.getCommission());
    }

    return commissions;
  }
}
