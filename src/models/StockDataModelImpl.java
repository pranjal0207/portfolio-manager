package models;


import java.util.Date;

/**
 * This class implements StockDataModel and all its functionality.
 * This class stores all the price data for a {@link StockModel} class
 * for a particular date.
 */
public class StockDataModelImpl implements StockDataModel {

  private final String tickerName;
  private final Date date;
  private final double closePrice;
  private final double openPrice;
  private final double highPrice;
  private final double lowPrice;
  private final long quantity;

  public StockDataModelImpl(String tickerName, Date date, double openPrice,
                            double highPrice, double lowPrice, double closePrice, long quantity) {
    this.tickerName = tickerName;
    this.date = date;
    this.closePrice = closePrice;
    this.lowPrice = lowPrice;
    this.openPrice = openPrice;
    this.highPrice = highPrice;
    this.quantity = quantity;
  }

  @Override
  public String getTickerName() {
    return this.tickerName;
  }

  @Override
  public double getClosePrice() {
    return this.closePrice;
  }

  @Override
  public double getHighPrice() {
    return this.highPrice;
  }

  @Override
  public double getLowPrice() {
    return this.lowPrice;
  }

  @Override
  public double getOpenPrice() {
    return this.openPrice;
  }

  @Override
  public long getQuantity() {
    return this.quantity;
  }

  @Override
  public Date getDate() {
    return this.date;
  }

  }
