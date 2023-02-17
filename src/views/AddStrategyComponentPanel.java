package views;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePickerImpl;


import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Features;
import models.Strategy;
import utils.DateRangeCalculatorUtil;

/**
 * Panel class to show add strategy component option.
 * This panel extends the jpanel and all its features.
 */
public class AddStrategyComponentPanel extends JPanel {
  private JDatePickerImpl dpStartDate;
  private JDatePickerImpl dpEndDate;
  private JTextField tFrequencyCount;
  private JTextField tStrategyName;
  private JTextField tCommission;
  private JTextField tAmount;

  private JComboBox<String> cbStrategyTypes;
  private JButton bAddStock;
  private JPanel pStockList;
  private List<StrategyItemPanel> strategyItemPanels;
  private final List<String> stocks;
  private final List<Strategy> strategyTypes;

  /**
   * Public constructor to add a strategy component panel.
   * @param stocks  all the stocks available.
   * @param strategyTypes strategies available.
   */
  public AddStrategyComponentPanel(List<String> stocks, List<Strategy> strategyTypes) {
    this.stocks = stocks;
    this.strategyTypes = strategyTypes;
    initializeViews();
  }

  private void initializeViews() {
    strategyItemPanels = new ArrayList<>();
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    JLabel lStrategyType = new JLabel("Strategy Type", SwingConstants.CENTER);
    lStrategyType.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lCommission = new JLabel("Commission", SwingConstants.CENTER);
    lCommission.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lStrategyName = new JLabel("Strategy Name", SwingConstants.CENTER);
    lStrategyName.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lStartDate = new JLabel("Start Date", SwingConstants.CENTER);
    lStartDate.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lEndDate = new JLabel("End Date", SwingConstants.CENTER);
    lEndDate.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lFrequency = new JLabel("Days", SwingConstants.CENTER);
    lFrequency.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lEvery = new JLabel("Every", SwingConstants.CENTER);
    lEvery.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lAmount = new JLabel("Amount", SwingConstants.CENTER);
    lAmount.setAlignmentX(Component.CENTER_ALIGNMENT);

    dpStartDate = new JDatePickerImpl(CustomJDatePanel.getCustomJDatePanel(),
            new DateComponentFormatter());

    dpEndDate = new JDatePickerImpl(CustomJDatePanel.getCustomJDatePanel(),
            new DateComponentFormatter());
    tFrequencyCount = new JTextField();
    tStrategyName = new JTextField();
    tCommission = new JTextField("0");
    tAmount = new JTextField();

    cbStrategyTypes = new JComboBox<>(this.strategyTypes.stream()
            .map(Strategy::toString).toArray(String[]::new));

    bAddStock = new JButton("Add another Stock");
    bAddStock.setAlignmentX(Component.CENTER_ALIGNMENT);

    pStockList = new JPanel();
    pStockList.setLayout(new GridLayout(0, 1));
    StrategyItemPanel strategyItemPanel = new StrategyItemPanel(stocks);
    pStockList.add(strategyItemPanel);
    strategyItemPanels.add(strategyItemPanel);

    this.add(lStrategyType);
    this.add(cbStrategyTypes);
    this.add(lStrategyName);
    this.add(tStrategyName);
    this.add(lAmount);
    this.add(tAmount);
    this.add(lCommission);
    this.add(tCommission);
    this.add(lStartDate);
    this.add(dpStartDate);
    this.add(lEndDate);
    this.add(dpEndDate);
    this.add(lEvery);
    this.add(tFrequencyCount);
    this.add(lFrequency);
    this.add(pStockList);
    this.add(bAddStock);
  }

  protected void addFeatures(Features features) {
    bAddStock.addActionListener((e) -> features.requestMoreStockInputForStrategy());
  }

  protected String getStrategyType() {
    return Objects.requireNonNull(cbStrategyTypes.getSelectedItem()).toString();
  }

  protected String getStrategyName() {
    return tStrategyName.getText();
  }

  protected String getAmount() {
    return tAmount.getText();
  }

  protected String getCommission() {
    return tCommission.getText();
  }

  protected String getStartDate() {
    if (this.dpStartDate.getModel().getValue() != null) {
      String date = this.dpStartDate.getModel().getValue().toString();
      return DateRangeCalculatorUtil.dateConverter(date,
              "EEE MMM dd HH:mm:ss zzzz yyyy", "yyyy-MM-dd");
    } else {
      return "";
    }
  }

  protected String getEndDate() {
    if (this.dpEndDate.getModel().getValue() != null) {
      String date = this.dpEndDate.getModel().getValue().toString();
      return DateRangeCalculatorUtil.dateConverter(date,
              "EEE MMM dd HH:mm:ss zzzz yyyy", "yyyy-MM-dd");
    } else {
      return "";
    }

  }

  protected String getFrequency() {
    return tFrequencyCount.getText();
  }

  protected void addStock() {
    StrategyItemPanel strategyItemPanel = new StrategyItemPanel(this.stocks);
    this.pStockList.add(strategyItemPanel);
    this.strategyItemPanels.add(strategyItemPanel);
    this.pStockList.validate();
  }

  protected List<String> getStockNames() {
    List<String> stockNames = new ArrayList<>();
    for (StrategyItemPanel strategyItemPanel : strategyItemPanels) {
      stockNames.add(strategyItemPanel.getStockName());
    }

    return stockNames;
  }

  protected List<String> getWeights() {
    List<String> weights = new ArrayList<>();
    for (StrategyItemPanel strategyItemPanel : strategyItemPanels) {
      weights.add(strategyItemPanel.getWeight());
    }

    return weights;
  }
}
