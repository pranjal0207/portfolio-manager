package views;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import java.awt.Component;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.Features;
import utils.DateRangeCalculatorUtil;

/**
 *  Panel class to show the modify portfolio panel. It extends JPanel ad all its features.
 */
public class ModifyPortfolioPanel extends JPanel {
  private JTextField tQuantity;
  private JTextField tCommission;
  private final List<String> stocks;
  private JComboBox<String> cbStocks;
  private JComboBox<String> cbBuySell;
  private final String portfolioName;
  private final Map<String, Double> portfolioElements;
  private JButton bSave;
  private JDatePickerImpl dpDate;
  private JLabel lProcessing;

  /**
   * Public constructor to initialise modify portfolio panel.
   * @param portfolioName name of portfolio.
   * @param portfolioElements map of all the portfolio elements.
   * @param stocks list of stocks available.
   */
  public ModifyPortfolioPanel(String portfolioName, Map<String, Double> portfolioElements,
                              List<String> stocks) {
    this.portfolioName = portfolioName;
    this.portfolioElements = portfolioElements;
    this.stocks = stocks;
    initializeView();
  }

  private void initializeView() {
    JLabel lModifyPortfolio = new JLabel(portfolioName, JLabel.CENTER);
    lModifyPortfolio.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel lCurrentComposition = new JLabel("Current Composition", JLabel.CENTER);
    lCurrentComposition.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lQuantity = new JLabel("Quantity", JLabel.CENTER);
    lQuantity.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel lCommission = new JLabel("Commission", JLabel.CENTER);
    lCommission.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel lDate = new JLabel("Date", JLabel.CENTER);
    lDate.setAlignmentX(Component.CENTER_ALIGNMENT);

    bSave = new JButton("Save");
    bSave.setAlignmentX(Component.CENTER_ALIGNMENT);

    tQuantity = new JTextField();
    tCommission = new JTextField("0");
    DateModel<Date> dateModel = new UtilDateModel();
    Properties properties = new Properties();
    properties.put("text.today", "Today");
    properties.put("text.month", "Month");
    properties.put("text.year", "Year");
    JDatePanelImpl jDatePanel = new JDatePanelImpl(dateModel, properties);
    dpDate = new JDatePickerImpl(jDatePanel, new DateComponentFormatter());

    JTable tCurrentComposition = new JTable(getData(), getColumns());
    tCurrentComposition.setDefaultEditor(Objects.class, null);
    tCurrentComposition.setEnabled(false);

    String[] cbBuySellOptions = {"BUY", "SELL"};
    cbBuySell = new JComboBox<>(cbBuySellOptions);

    lProcessing = new JLabel("<html>The biggest risk of all is not taking one<br><br></html>", JLabel.CENTER);
    lProcessing.setAlignmentX(Component.CENTER_ALIGNMENT);

    Collections.sort(this.stocks);
    cbStocks = new JComboBox<>(this.stocks.toArray(new String[0]));

    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    this.add(lModifyPortfolio);
    this.add(lCurrentComposition);
    this.add(tCurrentComposition);
    this.add(cbBuySell);
    this.add(cbStocks);
    this.add(lDate);
    this.add(dpDate);
    this.add(lQuantity);
    this.add(tQuantity);
    this.add(lCommission);
    this.add(tCommission);
    this.add(bSave);
    this.add(lProcessing);
  }

  protected void addFeatures(Features features) {
    bSave.addActionListener((e) -> features.modifyPortfolio(portfolioName, getStockTicker(),
            getQuantity(), getCommission(), getBuySellOption(), getDate()));
  }

  private Object[][] getData() {
    String[][] data = new String[portfolioElements.size() + 1][2];
    data[0][0] = "Stock Name";
    data[0][1] = "Quantity";
    int i = 1;
    for (Map.Entry<String, Double> entry : portfolioElements.entrySet()) {
      data[i][0] = entry.getKey();
      data[i][1] = String.valueOf(entry.getValue());
      i++;
    }

    return data;
  }

  private Object[] getColumns() {
    return new String[]{"Ticker Name", "Quantity"};
  }

  private String getBuySellOption() {
    return Objects.requireNonNull(this.cbBuySell.getSelectedItem()).toString();
  }

  private String getStockTicker() {
    return Objects.requireNonNull(this.cbStocks.getSelectedItem()).toString();
  }

  private String getQuantity() {
    return this.tQuantity.getText();
  }

  private String getCommission() {
    return this.tCommission.getText();
  }

  private String getDate() {
    if (this.dpDate.getModel().getValue() != null) {
      String date = this.dpDate.getModel().getValue().toString();
      return DateRangeCalculatorUtil.dateConverter(date,
              "EEE MMM dd HH:mm:ss zzzz yyyy", "yyyy-MM-dd");
    } else {
      return "";
    }
  }

  protected void showProcessing() {
    lProcessing.setText("Processing...Please wait");
    lProcessing.paintImmediately(lProcessing.getVisibleRect());
  }

  protected void clearProcessing()  {
    lProcessing.setText("<html>The biggest risk of all is not taking one<br><br></html>");
    lProcessing.paintImmediately(lProcessing.getVisibleRect());
  }

}
