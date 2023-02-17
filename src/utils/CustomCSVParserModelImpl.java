package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.StockDataModel;
import models.StockDataModelImpl;
import models.StockModel;
import models.StockModelImpl;

/**
 * This class implements CustomCSVParserModel and all its functionality.
 * This class reads the data from csv files for stock information and
 * returns accordingly.
 */
public class CustomCSVParserModelImpl implements CustomCSVParserModel {
  @Override
  public List<StockDataModel> toListOfStockDataModel(String input, String tickerName)
          throws RuntimeException {
    String[] lines = input.split("\r\n");
    List<StockDataModel> stockData = new ArrayList<>();
    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      String[] csvParts = line.split(",");
      try {
        StockDataModel dataModel = null;
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(csvParts[0]);
        double openPrice = Double.parseDouble(csvParts[1]);
        double highestPrice = Double.parseDouble(csvParts[2]);
        double lowestPrice = Double.parseDouble(csvParts[3]);
        double closingPrice = Double.parseDouble(csvParts[4]);
        long volume = Integer.parseInt(csvParts[5]);
        dataModel = new
                StockDataModelImpl(tickerName, date, openPrice, highestPrice, lowestPrice, closingPrice, volume);
        stockData.add(dataModel);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
    return stockData;
  }

  @Override
  public List<StockModel> toListOfStocks(String input) throws RuntimeException {
    String[] lines = input.split("\r\n");
    List<StockModel> stockData = new ArrayList<>();
    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      String[] csvParts = line.split(",");
      try {
        StockModel stockModel = null;
        String tickerName = csvParts[0];
        String stockName = csvParts[1];
        String exchangeName = csvParts[2];
        String assetType = csvParts[3];
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(csvParts[4]);
        if (assetType.equals("Stock")) {
          stockModel = StockModelImpl.getBuilder()
                  .stockTicker(tickerName)
                  .stockName(stockName)
                  .exchangeName(exchangeName)
                  .ipoDate(date)
                  .build();
          stockData.add(stockModel);
        }
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
    return stockData;
  }
}
