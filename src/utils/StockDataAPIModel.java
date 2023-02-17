package utils;

import java.util.List;

import models.StockDataModel;
import models.StockModel;

/**
 * This interface represents all the methods that can be done on
 * a stock data api to return different types of data.
 */
public interface StockDataAPIModel {
  /**
   * Get list of stocks available online and various prices associated for the same
   * for a timeseries.
   *
   * @param tickerName stock for which data needs to be extracted.
   * @return a list of stock data model objects with various prices for pre definite date range.
   */
  List<StockDataModel> getTimeSeriesDataForAStock(String tickerName);

  /**
   * Get all the stocks supported by the system.
   *
   * @return a list of stock model objects of all the supported stocks.
   */
  List<StockModel> getSupportedStocks();
}
