package views;


import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *  Panel class to show the strategy item panel. It extends JPanel ad all its features.
 */
public class StrategyItemPanel extends JPanel {
  JLabel lStockName;
  JLabel lWeight;

  JComboBox<String> cbStocks;
  JTextField tWeight;

  List<String> stocks;

  /**
   * Public constructor to initialise the strategy item panel.
   * @param stocks list of stocks available.
   */
  public StrategyItemPanel(List<String> stocks) {
    this.stocks = stocks;
    initializeViews();
  }

  private void initializeViews() {
    lStockName = new JLabel("Stock Name");
    lWeight = new JLabel("Weight (%)");

    Collections.sort(this.stocks);
    cbStocks = new JComboBox<>(this.stocks.toArray(new String[0]));

    tWeight = new JTextField();

    this.setLayout(new GridLayout(0, 2));

    this.add(lStockName);
    this.add(lWeight);

    this.add(cbStocks);
    this.add(tWeight);
  }

  protected String getStockName() {
    return Objects.requireNonNull(this.cbStocks.getSelectedItem()).toString();
  }

  protected String getWeight() {
    return this.tWeight.getText();
  }
}
