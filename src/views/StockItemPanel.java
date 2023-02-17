package views;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePickerImpl;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import javax.swing.*;

import utils.DateRangeCalculatorUtil;

/**
 *  Panel class to show the stock item panel. It extends JPanel ad all its features.
 */
public class StockItemPanel extends JPanel {
  JLabel lStockName;
  JLabel lQuantity;
  JLabel lCommission;
  JLabel lDate;
  JLabel lBroker;

  JComboBox<String> cbStocks;
  JTextField tQuantity;
  JTextField tCommission;
  List<String> stocks;

  JComboBox<String> cbBrokers;
  private JDatePickerImpl dpDate;
  Map<String, Double> brokerCommissions;

  /**
   * Public constructor to show the stock item panel.
   * @param stocks list of stocks available.
   */
  public StockItemPanel(List<String> stocks, Map<String, Double> brokerCommissions) {
    this.stocks = stocks;
    this.brokerCommissions = brokerCommissions;
    initializeViews();
  }

  private void initializeViews() {
    lStockName = new JLabel("Stock Name");
    lQuantity = new JLabel("Quantity");
    lBroker = new JLabel("Brokerage");
    lCommission = new JLabel("Commission");
    lDate = new JLabel("Date");

    cbBrokers = new JComboBox<>(brokerCommissions.keySet().toArray(new String[0]));

    Collections.sort(this.stocks);
    cbStocks = new JComboBox<>(this.stocks.toArray(new String[0]));

    tQuantity = new JTextField();
    tCommission = new JTextField(brokerCommissions.get(
            Objects.requireNonNull(cbBrokers.getSelectedItem()).toString()).toString());
    tCommission.setEditable(false);
    dpDate = new JDatePickerImpl(CustomJDatePanel.getCustomJDatePanel(),
            new DateComponentFormatter());

    this.setLayout(new GridLayout(0, 5));

    this.add(lStockName);
    this.add(lQuantity);
    this.add(lBroker);
    this.add(lCommission);
    this.add(lDate);

    this.add(cbStocks);
    this.add(tQuantity);
    this.add(cbBrokers);
    this.add(tCommission);
    this.add(dpDate);

    cbBrokers.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        tCommission.setText(brokerCommissions.get(e.getItem().toString()).toString());
      }
    });
  }

  protected String getStockName() {
    return Objects.requireNonNull(this.cbStocks.getSelectedItem()).toString();
  }

  protected String getQuantity() {
    return this.tQuantity.getText();
  }

  protected String getCommission() {
    return this.tCommission.getText();
  }

  protected String getDate() {
    if (this.dpDate.getModel() != null && this.dpDate.getModel().getValue() != null) {
      String date = this.dpDate.getModel().getValue().toString();
      return DateRangeCalculatorUtil.dateConverter(date,
              "EEE MMM dd HH:mm:ss zzzz yyyy", "yyyy-MM-dd");
    } else {
      return "";
    }
  }
}
