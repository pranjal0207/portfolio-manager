package utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.User;
import models.StockDataModel;
import models.StockModel;


public class DBHandler {
  private Connection connection;

  public DBHandler(String userName, String password) throws SQLException {
    Connection connection = null;
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio_manager",
              userName, password);
    this.connection = connection;
  }

  public User loginUser(String userName, String password) throws SQLException {
    User user = null;
    String query = "{ call login_user(?,?) }";
      ResultSet resultSet;
      CallableStatement callableStatement = connection.prepareCall(query);

      callableStatement.setString(1, userName);
      callableStatement.setString(2, password);
      resultSet = callableStatement.executeQuery();

      while (resultSet.next()) {
        user = new User(userName, resultSet.getInt("id"));
      }
      resultSet.close();
      return user;
  }

  public User signUpUser(String userName, String password) throws SQLException {
    User user = null;
    String query = "{ call insert_new_user(?,?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);

    callableStatement.setString(1, userName);
    callableStatement.setString(2, password);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      user = new User(userName, resultSet.getInt("id"));
    }
    resultSet.close();
    return user;
  }

  public List<String> getAllPortfolios(int userId) throws SQLException {
    List<String> portfolios = new ArrayList<>();
    String query = "{ call get_all_portfolios(?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);

    callableStatement.setString(1, String.valueOf(userId));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      portfolios.add(resultSet.getString("pname"));
    }
    resultSet.close();
    return portfolios;
  }

  public int getTotalStocks() throws SQLException {
    int total_stocks = 0;
    String query = "{ call get_total_stocks() }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      total_stocks = resultSet.getInt("total_stocks");
    }
    resultSet.close();
    return total_stocks;
  }

  public List<String> getAllStocks() throws SQLException {
    List<String> stockNames = new ArrayList<>();
    String query = "{ call get_all_stocks() }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      stockNames.add(resultSet.getString("stock_ticker"));
    }
    resultSet.close();
    return stockNames;
  }

  public void insertStocks(List<StockModel> stocks) throws SQLException {
    for(StockModel model : stocks) {
      String query = "{ call insert_new_stock(?,?,?,?,?) }";
      ResultSet resultSet;
      CallableStatement callableStatement = connection.prepareCall(query);

      callableStatement.setString(1, model.getStockTicker());
      callableStatement.setString(2, model.getStockName());
      callableStatement.setString(3, model.getExchangeName());
      callableStatement.setDate(4, new Date(model.getIpoDate().getTime()));
      callableStatement.setString(5, model.getStatus());
      resultSet = callableStatement.executeQuery();
      resultSet.close();
    }
  }

  public int createPortfolio(String portfolioName, int userId, java.util.Date date) throws SQLException {
    int portfolioId = 0;
    String query = "{ call insert_new_portfolio(?,?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setString(1, portfolioName);
    callableStatement.setInt(2, userId);
    callableStatement.setDate(3, new Date(date.getTime()));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      portfolioId = resultSet.getInt("pid");
    }
    resultSet.close();
    return portfolioId;
  }

  public java.util.Date getMaxAvailableStockDataDate(String tickerName) throws SQLException {
    java.util.Date maxDate = null;
    String query = "{ call get_max_available_stock_data_date(?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setString(1, tickerName);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      maxDate = new java.util.Date(resultSet.getDate("date").getTime());
    }
    resultSet.close();
    return maxDate;
  }

  public void insertStockData(String tickerName, List<StockDataModel> data) throws SQLException {
    for(StockDataModel model : data) {
      String query = "{ call insert_new_stock_data(?,?,?,?,?,?,?) }";
      ResultSet resultSet;
      CallableStatement callableStatement = connection.prepareCall(query);
      callableStatement.setString(1, tickerName);
      callableStatement.setDate(2, new Date(model.getDate().getTime()));
      callableStatement.setDouble(3, model.getOpenPrice());
      callableStatement.setDouble(4, model.getClosePrice());
      callableStatement.setDouble(5, model.getLowPrice());
      callableStatement.setDouble(6, model.getHighPrice());
      callableStatement.setDouble(7, model.getQuantity());
      resultSet = callableStatement.executeQuery();
      resultSet.close();
    }
  }

  public double getStockPriceOnADate(String tickerName, java.util.Date transactionDate) throws SQLException {
    double stockPrice = 0;
    String query = "{ call get_stock_data_for_date(?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setString(1, tickerName);
    callableStatement.setDate(2, new Date(transactionDate.getTime()));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      stockPrice = resultSet.getDouble("close");
    }
    resultSet.close();
    return stockPrice;
  }

  public void addTransaction(int portfolioId, String tickerName,
                             String transactionType, int quantity, double price,
                             java.util.Date transactionDate, double commission) throws SQLException {
    String query = "{ call insert_new_transaction(?,?,?,?,?,?,?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    callableStatement.setString(1, tickerName);
    callableStatement.setInt(2, portfolioId);
    callableStatement.setString(3, transactionType);
    callableStatement.setDouble(4, quantity);
    callableStatement.setDouble(5, price);
    callableStatement.setDate(6, new Date(transactionDate.getTime()));
    callableStatement.setDouble(7, commission);
    resultSet = callableStatement.executeQuery();
    resultSet.close();
  }

  public int getPortfolioId(int userId, String portfolioName) throws SQLException {
    int portfolioId = 0;
    String query = "{ call get_portfolio_id(?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, portfolioName);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      portfolioId = resultSet.getInt("pid");
    }
    resultSet.close();
    return portfolioId;
  }

  public int addStrategy(String strategyName, int portfolioId, java.util.Date startDate, 
                         java.util.Date endDate, double amount, double commission, int frequency) throws SQLException {
    int strategyId = 0;
    String query = "{ call insert_new_strategy(?,?,?,?,?,?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setString(1, strategyName);
    callableStatement.setInt(2, portfolioId);
    callableStatement.setDate(3, new Date(startDate.getTime()));
    callableStatement.setDate(4, new Date(endDate.getTime()));
    callableStatement.setDouble(5, amount);
    callableStatement.setDouble(6, commission);
    callableStatement.setInt(7, frequency);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      strategyId = resultSet.getInt("sid");
    }
    resultSet.close();
    return strategyId;
  }

  public void addStrategyElement(int strategyId, String stockName, Double weight) throws SQLException {
    String query = "{ call insert_new_strategy_element(?,?,?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    callableStatement.setInt(1, strategyId);
    callableStatement.setString(2, stockName);
    callableStatement.setDouble(3, weight);
    resultSet = callableStatement.executeQuery();
    resultSet.close();
  }

  public void runStrategy(int strategyId) throws SQLException {
    String query = "{ call run_strategy(?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    callableStatement.setInt(1, strategyId);
    resultSet = callableStatement.executeQuery();
    resultSet.close();
  }

  public void deletePortfolio(int userId, String portfolioName) throws SQLException {
    String query = "{ call delete_portfolio(?,?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, portfolioName);
    resultSet = callableStatement.executeQuery();
    resultSet.close();
  }

  public List<List<String>> getPortfolioComposition(int userId, String portfolioName, java.util.Date date) throws SQLException {
    List<List<String>> composition = new ArrayList<>();
    String query = "{ call get_portfolio_composition(?,?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, portfolioName);
    callableStatement.setDate(3, new Date(date.getTime()));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      List<String> compositionElement = new ArrayList<>();
      String stockTicker = resultSet.getString("stock_ticker");
      String stockName = resultSet.getString("stock_name");
      double averagePrice = resultSet.getDouble("avg_price");
      double quantity = resultSet.getDouble("total_quantity");
      Date lastTransactionDate = resultSet.getDate("last_transaction_date");

      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      format.setLenient(false);

      compositionElement.add(stockTicker);
      compositionElement.add(stockName);
      compositionElement.add(String.valueOf(quantity));
      compositionElement.add(String.valueOf(averagePrice));
      compositionElement.add(format.format(lastTransactionDate));
      composition.add(compositionElement);
    }
    resultSet.close();
    return composition;
  }

  public double getPortfolioValueByDate(int userId, String portfolioName, java.util.Date date) throws SQLException {
    double valueOnADate = 0;
    String query = "{ call get_portfolio_value_on_a_date(?,?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, portfolioName);
    callableStatement.setDate(3, new Date(date.getTime()));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      valueOnADate = resultSet.getInt("value");
    }
    resultSet.close();
    return valueOnADate;
  }

  public double getCostBasis(int userId, String portfolioName, java.util.Date date) throws SQLException {
    double valueOnADate = 0;
    String query = "{ call get_portfolio_cost_basis(?,?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, portfolioName);
    callableStatement.setDate(3, new Date(date.getTime()));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      valueOnADate = resultSet.getInt("cost_basis");
    }
    resultSet.close();
    return valueOnADate;
  }

  public Map<String, Double> getPortfolioElementsForModify(int userId, String portfolioName) throws SQLException {
    Map<String, Double> portfolioElements = new HashMap<>();
    String query = "{ call get_portfolio_composition(?,?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, portfolioName);
    callableStatement.setDate(3, new Date(new java.util.Date().getTime()));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      String stockTicker = resultSet.getString("stock_ticker");
      double quantity = resultSet.getDouble("total_quantity");
      portfolioElements.put(stockTicker, quantity);
    }
    resultSet.close();
    return portfolioElements;
  }

  public Map<java.util.Date, Double> getStockPrices(String stockName, java.util.Date startDateGraph,
                                                    java.util.Date endDateGraph) throws SQLException {
    Map<java.util.Date, Double> stockPriceDateMap = new HashMap<>();
    String query = "{ call get_stock_price_dates(?,?,?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setString(1, stockName);
    callableStatement.setDate(2, new Date(startDateGraph.getTime()));
    callableStatement.setDate(3, new Date(endDateGraph.getTime()));
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      Date date = resultSet.getDate("date");
      double price = resultSet.getDouble("price");
      stockPriceDateMap.put(date, price);
    }
    resultSet.close();
    return stockPriceDateMap;
  }

  public String[] getProfileDetails(int userId) throws SQLException {
    String[] profileDetails = new String[3];
    String query = "{ call get_profile_details(?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setInt(1, userId);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      profileDetails[0] = resultSet.getString("nick_name");
      profileDetails[1] = resultSet.getString("phone_number");
      profileDetails[2] = resultSet.getString("password");
    }
    resultSet.close();
    return profileDetails;
  }

  public void updateProfile(int userId, String nickName, String phoneNumber, String password) throws SQLException {
    String query = "{ call update_profile_details(?,?,?,?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, nickName);
    callableStatement.setString(3, phoneNumber);
    callableStatement.setString(4, password);
    resultSet = callableStatement.executeQuery();
    resultSet.close();
  }

  public List<String> getAllBrokers() throws SQLException {
    List<String> brokers = new ArrayList<>();
    String query = "{ call get_all_brokers() }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      brokers.add(resultSet.getString("broker_name"));
    }
    resultSet.close();
    return brokers;
  }

  public void addBroker(int userId, String broker) throws SQLException {
    String query = "{ call add_broker(?,?) }";
    ResultSet resultSet;
    CallableStatement callableStatement = connection.prepareCall(query);
    callableStatement.setInt(1, userId);
    callableStatement.setString(2, broker);
    resultSet = callableStatement.executeQuery();
    resultSet.close();
  }

  public Map<String, Double> getBrokerCommissions(int userId) throws SQLException {
    Map<String, Double> commissionMap = new HashMap<>();
    String query = "{ call get_brokers_for_user(?) }";
    CallableStatement callableStatement = connection.prepareCall(query);
    ResultSet resultSet;
    callableStatement.setInt(1, userId);
    resultSet = callableStatement.executeQuery();

    while (resultSet.next()) {
      String brokerName = resultSet.getString("broker_name");
      double price = resultSet.getDouble("commission");
      commissionMap.put(brokerName, price);
    }
    resultSet.close();
    return commissionMap;
  }
}
