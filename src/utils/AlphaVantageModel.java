package utils;

import java.util.List;

import models.StockDataModel;
import models.StockModel;

/**
 * This class implements the Stock Data API Model.
 * It has methods which call Alpha Vantage API to extract
 * the requested data for particular stock.
 */
public class AlphaVantageModel implements StockDataAPIModel {
  private final String apiKey = "WMDORK6BEVBQ7K7S";
  private final HttpRequestModel httpRequestModel;

  /**
   * Public constructor to initialize the alpha vantage model.
   */
  public AlphaVantageModel() {
    httpRequestModel = new HttpRequestModelImpl();
  }

  @Override
  public List<StockDataModel> getTimeSeriesDataForAStock(String tickerName)
          throws RuntimeException {
    String result = httpRequestModel.fetchUrl(generateUrlForTimeSeriesData(tickerName));
    return new CustomCSVParserModelImpl().toListOfStockDataModel(result, tickerName);
  }

  @Override
  public List<StockModel> getSupportedStocks() {
    String result = httpRequestModel.fetchUrl(generateUrlForStocksListData());
    return new CustomCSVParserModelImpl().toListOfStocks(result);
  }

  private String generateUrlForTimeSeriesData(String tickerName) {
    return "https://www.alphavantage"
            + ".co/query?function=TIME_SERIES_DAILY"
            + "&outputsize=full"
            + "&symbol"
            + "="
            + tickerName
            + "&apikey="
            + this.apiKey
            + "&datatype=csv";
  }

  private String generateUrlForStocksListData() {
    return "https://www.alphavantage"
            + ".co/query?function=LISTING_STATUS"
            + "&apikey="
            + this.apiKey;
  }
}