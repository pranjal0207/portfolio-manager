package views;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.swing.*;

import controller.Features;
import utils.ChartPlot;
import utils.DateRangeCalculatorUtil;

public class StockDetailsPanel extends JPanel {

  private JButton bBack;
  private JButton bShow;
  private StockDetailsCard stockDetailsCard;
  private JComboBox<String> cbStocks;
  private List<String> stocks;

  private JDatePickerImpl dpStartDate;
  private JDatePickerImpl dpEndDate;

  public StockDetailsPanel() {
    this.stocks = new ArrayList<>();
    initializeViews();
  }

  protected void setStocks(List<String> stocks) {
    this.stocks = stocks;
    if(cbStocks.getItemCount() == 0) {
      for (String stock : stocks) {
        cbStocks.addItem(stock);
      }
    }
  }

  private void initializeViews() {
    bBack = new JButton("Back");
    bShow = new JButton("Show Graph");
    stockDetailsCard = new StockDetailsCard();
    cbStocks = new JComboBox<>(stocks.toArray(new String[0]));

    DateModel<Date> dateModel = new UtilDateModel();
    Properties properties = new Properties();
    properties.put("text.today", "Today");
    properties.put("text.month", "Month");
    properties.put("text.year", "Year");
    JDatePanelImpl jDatePanel = new JDatePanelImpl(dateModel, properties);
    dpStartDate = new JDatePickerImpl(jDatePanel, new DateComponentFormatter());

    DateModel<Date> dateModelEnd = new UtilDateModel();
    Properties propertiesEnd = new Properties();
    propertiesEnd.put("text.today", "Today");
    propertiesEnd.put("text.month", "Month");
    propertiesEnd.put("text.year", "Year");
    JDatePanelImpl jDatePanelEnd = new JDatePanelImpl(dateModelEnd, propertiesEnd);
    dpEndDate = new JDatePickerImpl(jDatePanelEnd, new DateComponentFormatter());

    JPanel secondPanel = new JPanel();
    secondPanel.add(cbStocks);
    secondPanel.add(dpStartDate);
    secondPanel.add(dpEndDate);
    secondPanel.add(bShow);
    secondPanel.add(bBack);

    this.setLayout(new BorderLayout());
    this.add(secondPanel, BorderLayout.PAGE_START);
    this.add(stockDetailsCard, BorderLayout.CENTER);
  }

  protected void addFeatures(Features features) {
    bBack.addActionListener((e) -> features.goToMainMenu());
    bShow.addActionListener((e) -> features.showStocksGraph(
            Objects.requireNonNull(cbStocks.getSelectedItem()).toString(), getStartDate(), getEndDate()));
    stockDetailsCard.addFeatures(features);
  }

  protected void clearPage() {
    stockDetailsCard.clearPage();
  }

  protected void showGraph(ChartPlot chartPlot) {
    stockDetailsCard.showBarChart(chartPlot);
  }

  private String getEndDate() {
    if (this.dpEndDate.getModel().getValue() != null) {
      String date = this.dpEndDate.getModel().getValue().toString();
      return DateRangeCalculatorUtil.dateConverter(date,
              "EEE MMM dd HH:mm:ss zzzz yyyy", "yyyy-MM-dd");
    } else {
      return "";
    }
  }

  private String getStartDate() {
    if (this.dpStartDate.getModel().getValue() != null) {
      String date = this.dpStartDate.getModel().getValue().toString();
      return DateRangeCalculatorUtil.dateConverter(date,
              "EEE MMM dd HH:mm:ss zzzz yyyy", "yyyy-MM-dd");
    } else {
      return "";
    }
  }

  protected void showProcessing() {
    stockDetailsCard.showProcessing();
  }

  protected void clearProcessing() {
    stockDetailsCard.clearProcessing();
  }
}
