package utils;

import java.util.List;

import models.StockDataModel;
import models.StockModel;

/**
 * This interface represents all the functionalities that can
 * be done on a CSV file to fetch various types of Data for a stock
 * or list of stocks.
 */
public interface CustomCSVParserModel {
  List<StockDataModel> toListOfStockDataModel(String input, String tickerName)
          throws RuntimeException;

  /**
   * Helper Method to convert an input string to
   * list of {@link StockModel} objects.
   *
   * @param input comma separated input string of stock
   *              model
   * @return list of {@link StockModel}
   * @throws RuntimeException if the given input string is corrupted
   */
  List<StockModel> toListOfStocks(String input) throws RuntimeException;
}
