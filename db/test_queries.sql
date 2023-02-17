call insert_new_user("pranjal", "pranjal1234");
call insert_new_portfolio ("p1", 1, "2022-08-08");
call insert_new_stock ("ibm", "ibm", "NASDAQ", "2002-09-09", "Active");
call insert_new_stock ("goog", "goog", "NASDAQ", "2002-09-09", "Active");
call insert_new_stock_data("ibm", "2022-09-09", 20.2, 20.22, 20.222, 20.2222, 20.22222);
call insert_new_stock_data("ibm", "2022-09-19", 20.1, 20.11, 20.111, 20.1111, 20.11111);
call insert_new_stock_data("goog", "2022-09-09", 20.1, 20.11, 20.111, 20.1111, 20.11111);
call insert_new_stock_data("goog", "2022-09-10", 20.1, 20.11, 20.111, 20.1111, 20.11111);
call insert_new_transaction("goog", 1, "BUY", 10, 100.83, "2022-10-02", 0);
call insert_new_transaction("goog", 1, "SELL", 2, 100.83, "2022-11-25", 0);
call insert_new_transaction("ibm", 1, "BUY", 4, 5748, "2022-09-09", 30);
call insert_new_transaction("ibm", 1, "BUY", 2, 5748, "2022-09-09", 30);
call insert_new_transaction("ibm", 1, "SELL", 1, 5748, "2022-11-09", 30);
call insert_new_strategy("s1", "1", "2022-08-09", "2022-11-11", 1000, 20, 10);
call insert_new_strategy_element(1, "goog", 50);
call insert_new_strategy_element(1, "ibm", 50);

SELECT * FROM transaction;
SELECT * from portfolio;
select * from user;
select * from stock_data order by date desc;
select * from strategy;
select * from strategy_element;

call get_portfolio_composition(1, "p1", "2022-12-08");
call get_portfolio_cost_basis(1, "p1", "2022-12-08")