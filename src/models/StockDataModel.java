package models;

import java.util.Date;

/**
 * An interface which contains all the methods applicable on
 * various attributes of a Stock Data Model.
 */
public interface StockDataModel {
  /**
   * Get the stock model from the stock data model.
   *
   * @return a stock model object for stock data model.
   */
  String getTickerName();

  /**
   * Get the closing price for a stock.
   *
   * @return closing price of a stock model as double only.
   */
  double getClosePrice();

  /**
   * Get the high price for a stock.
   *
   * @return high price of a stock model as double only.
   */
  double getHighPrice();

  /**
   * Get the low price for a stock.
   *
   * @return closilowng price of a stock model as double only.
   */
  double getLowPrice();

  /**
   * Get the open price for a stock.
   *
   * @return open price of a stock model as double only.
   */
  double getOpenPrice();

  /**
   * Get the quantity for a stock.
   *
   * @return quantity of a stock model as long only.
   */
  long getQuantity();

  /**
   * Get the date of stock data model for which different prices
   * are stored.
   *
   * @return the date of stock data model as a date object only.
   */
  Date getDate();


}
