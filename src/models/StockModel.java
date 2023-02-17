package models;

import java.util.Date;

/**
 * This interface represents a stock model in the portfolio system.
 * This interface contains various operations that can be applied to
 * a particular stock.
 */
public interface StockModel {
  /**
   * Get the stock ticker symbol for a particular stock.
   *
   * @return a stock ticker symbol as string only.
   */
  String getStockTicker();

  /**
   * Get the stock name for a particular stock.
   *
   * @return a stock name as string only.
   */
  String getStockName();

  /**
   * Get the stock exchange name for a particular stock
   * where it is traded.
   *
   * @return a stock exchange name as string only.
   */
  String getExchangeName();

  /**
   * Get the stock IPO date for a particular stock.
   *
   * @return a Date object when stock got offered to public.
   */
  Date getIpoDate();

  /**
   * Get the stock delisting date for a particular stock.
   *
   * @return a Date object when stock got delisted to public.
   */
  Date getDelistingDate();

  /**
   * Get the status of a particular stock.
   *
   * @return a status of a stock as a string only.
   */
  String getStatus();

  /**
   * Get the asset type of particular stock.
   *
   * @return the asset type of stock as a string only.
   */
  String getAssetType();

}
