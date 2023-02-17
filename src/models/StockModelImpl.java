package models;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import utils.CustomCSVParserModelImpl;

/**
 * This class implements StockModel and all its functionality.
 * This class stores all the information for a {@link StockModel}.
 */
public class StockModelImpl implements StockModel {

  private final String stockTicker;

  private final String stockName;

  private final String exchangeName;

  private final Date ipoDate;

  private final String assetType;
  private final Date delistingDate;
  private final String status;

  /**
   * Constructor to assign values to attributes of Stock Model.
   *
   * @param stockTicker  stock ticker symbol to be assigned.
   * @param stockName    stock ticker name to be assigned.
   * @param exchangeName exchange name where stock is traded.
   * @param ipoDate      date when stock was made public.
   */
  public StockModelImpl(String stockTicker, String stockName, String exchangeName, Date ipoDate) {
    this.stockTicker = stockTicker;
    this.stockName = stockName;
    this.exchangeName = exchangeName;
    this.ipoDate = ipoDate;
    this.assetType = "Stock";
    this.delistingDate = null;
    this.status = "Active";
  }

  /**
   * Static class to get a builder to build
   * Stock Model class.
   *
   * @return a new builder object for Stock Model.
   */
  public static StockModelBuilder getBuilder() {
    return new StockModelBuilder();
  }

  @Override
  public String getStockTicker() {
    return this.stockTicker;
  }

  @Override
  public String getStockName() {
    return this.stockName;
  }

  @Override
  public String getExchangeName() {
    return this.exchangeName;
  }

  @Override
  public Date getIpoDate() {
    return this.ipoDate;
  }

  @Override
  public Date getDelistingDate() {
    return this.delistingDate;
  }

  @Override
  public String getStatus() {
    return this.status;
  }

  @Override
  public String getAssetType() {
    return this.assetType;
  }

  /**
   * Builder helper class to build an object of Stock Data Model class.
   * It contains functionality to set different fields of Stock Data Model.
   */
  public static class StockModelBuilder {
    private String stockTicker;

    private String stockName;

    private String exchangeName;

    private Date ipoDate;

    /**
     * Initialises a stock model builder with initial empty values.
     */
    private StockModelBuilder() {
      this.stockTicker = "";
      this.stockName = "";
      this.exchangeName = "";
      this.ipoDate = null;
    }

    /**
     * Set stock ticker symbol of builder object to be further assigned
     * to User Set Model.
     *
     * @param stockTicker stock symbol to be assigned.
     * @return the resulting builder object with stock symbol assigned.
     */
    public StockModelBuilder stockTicker(String stockTicker) {
      this.stockTicker = stockTicker;
      return this;
    }

    /**
     * Set stockName of builder object to be further assigned
     * to User Set Model.
     *
     * @param stockName stock name to be assigned.
     * @return the resulting builder object with stock name assigned.
     */
    public StockModelBuilder stockName(String stockName) {
      this.stockName = stockName;
      return this;
    }

    /**
     * Set exchangeName of builder object to be further assigned
     * to User Set Model.
     *
     * @param exchangeName exchange name to be assigned.
     * @return the resulting builder object with exchange name assigned.
     */
    public StockModelBuilder exchangeName(String exchangeName) {
      this.exchangeName = exchangeName;
      return this;
    }

    /**
     * Set ipoDate of builder object to be further assigned
     * to User Set Model.
     *
     * @param ipoDate ipoDate to be assigned.
     * @return the resulting builder object with ipoDate assigned.
     */
    public StockModelBuilder ipoDate(Date ipoDate) {
      this.ipoDate = ipoDate;
      return this;
    }

    /**
     * Build a Stock Model builder object to get Stock Model.
     *
     * @return a new Stock Model object with all users appended
     *         in the list.
     */
    public StockModelImpl build() {
      return new StockModelImpl(this.stockTicker, this.stockName, this.exchangeName, this.ipoDate);
    }

  }

}
