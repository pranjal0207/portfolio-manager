USE portfolio_manager;


DROP PROCEDURE IF EXISTS check_if_user_exists;
DELIMITER //
CREATE PROCEDURE check_if_user_exists(
    usernamein VARCHAR(20),
    passwordin VARCHAR(256)
)
BEGIN
	DECLARE count_t INT;
    
	SELECT count(*) into count_t
    FROM user
    WHERE username = usernamein AND passwordin = password;
    
    IF count_t = 0
		THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid credentials';
    END IF;
END//
DELIMITER ;


-- login user
DROP PROCEDURE IF EXISTS login_user;
DELIMITER //
CREATE PROCEDURE login_user(
    usernamein VARCHAR(20), 
    passwordin VARCHAR(256)
)
BEGIN
	DECLARE count_t INT;
    
    call check_if_user_exists(usernamein, passwordin);
    
	SELECT id
    FROM user
    WHERE username = usernamein AND passwordin = password;
    
    
END//
DELIMITER ;


-- get all portfolios
DROP PROCEDURE IF EXISTS get_all_portfolios;
DELIMITER //
CREATE PROCEDURE get_all_portfolios(
    uidin INT
)
BEGIN
	SELECT *
    FROM portfolio
    WHERE user_id = uidin;
END//
DELIMITER ;


-- get number of stocks
DROP PROCEDURE IF EXISTS get_total_stocks;
DELIMITER //
CREATE PROCEDURE get_total_stocks()
BEGIN
	SELECT count(*) as total_stocks
    FROM stock;
END//
DELIMITER ;


-- get all stocks
DROP PROCEDURE IF EXISTS get_all_stocks;
DELIMITER //
CREATE PROCEDURE get_all_stocks()
BEGIN
	SELECT * 
    FROM stock;
END//
DELIMITER ;



-- get stock data
DROP PROCEDURE IF EXISTS get_stock_data;
DELIMITER //
CREATE PROCEDURE get_stock_data(
	stock_tickerin VARCHAR(20)
)
BEGIN
	SELECT * 
    FROM stock_data
    WHERE stock_ticker = stock_tickerin;
END //
DELIMITER ;


-- get_max available stock data date
DROP PROCEDURE IF EXISTS get_max_available_stock_data_date;
DELIMITER //
CREATE PROCEDURE get_max_available_stock_data_date(
	stock VARCHAR(20)
)
BEGIN
	SELECT date 
    FROM stock_data
    where stock_ticker = stock
    ORDER BY date DESC LIMIT 1;
END //
DELIMITER ;


-- get stock data for date
DROP PROCEDURE IF EXISTS get_stock_data_for_date;
DELIMITER //
CREATE PROCEDURE get_stock_data_for_date(
	stock VARCHAR(20),
    datein DATE
)
BEGIN
	SELECT *
    FROM stock_data
    WHERE date = datein AND stock_ticker = stock;
END //
DELIMITER ;



-- get portfolio id
DROP PROCEDURE IF EXISTS get_portfolio_id;
DELIMITER //
CREATE PROCEDURE get_portfolio_id(
	userid INT,
    portfolio_name VARCHAR(20)
)
BEGIN
	SELECT pid
    FROM portfolio
    WHERE user_id = userid AND pname = portfolio_name;
END //
DELIMITER ;


-- get portfolio composition
DROP PROCEDURE IF EXISTS get_portfolio_composition;
DELIMITER //
CREATE PROCEDURE get_portfolio_composition(
	userid INT,
    portfolioname VARCHAR(20), 
    date_in DATE
)
BEGIN
    DECLARE done INT DEFAULT 0;
    
	DECLARE pid_in INT;
    DECLARE stock_ticker_in VARCHAR(20);
    DECLARE stock_name_in VARCHAR(200);
    DECLARE latest_trans_date DATE;
    DECLARE quantity_in DOUBLE DEFAULT 0;
    DECLARE average_price_in DOUBLE;
    
    DECLARE cur CURSOR FOR 
		SELECT DISTINCT(stock_ticker)
        FROM transaction
        WHERE pid = (SELECT pid
			FROM portfolio
			WHERE user_id = userid AND pname = portfolioname
		) AND transaction_date <= date_in;
      
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    DROP TEMPORARY TABLE IF EXISTS temp_table;
    CREATE TEMPORARY TABLE temp_table(
		stock_ticker varchar(20),
        stock_name VARCHAR(200), 
        total_quantity DOUBLE, 
        avg_price DOUBLE, 
        last_transaction_date DATE
    );
    
    SELECT pid into pid_in
	FROM portfolio
	WHERE user_id = userid AND pname = portfolioname;
            
    OPEN cur;
    
	getComposition : LOOP
    
		FETCH cur INTO stock_ticker_in;
		
        IF done = 1 
			THEN LEAVE getComposition;
		END IF;
        
		SELECT stock_name into stock_name_in 
        FROM stock
		WHERE stock_ticker = stock_ticker_in;
        
        SELECT max(transaction_date) into latest_trans_date
		FROM transaction 
        WHERE pid = pid_in and stock_ticker = stock_ticker_in
        GROUP BY stock_ticker;
        
        SELECT quantity_stock_till_date(date_in, pid_in, stock_ticker_in) into quantity_in;
        SELECT stock_avg_till_date(pid_in, stock_ticker_in, date_in) into average_price_in;
            
		INSERT INTO temp_table values(stock_ticker_in, stock_name_in, quantity_in, average_price_in, latest_trans_date); 
            
    END LOOP getComposition;
    
    SELECT * FROM temp_table;
    
    DROP TEMPORARY TABLE temp_table;
END //
DELIMITER ;



-- get portdolio total value
DROP PROCEDURE IF EXISTS get_portfolio_value_on_a_date;
DELIMITER //
CREATE PROCEDURE get_portfolio_value_on_a_date(
	userid INT,
    portfolioname VARCHAR(20), 
    date_in DATE
)
BEGIN
	DECLARE done INT DEFAULT 0;
    
    DECLARE pid_in INT;
    DECLARE stock_ticker_in VARCHAR(20);
    DECLARE quant DOUBLE;
    DECLARE value DOUBLE;
    DECLARE total_value DOUBLE DEFAULT 0;
    
    DECLARE cur CURSOR FOR 
		SELECT DISTINCT(stock_ticker)
        FROM transaction
        WHERE pid = (
			SELECT pid 
			FROM portfolio
			WHERE user_id = userid and pname = portfolioname
		);
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    SELECT pid into pid_in
	FROM portfolio
	WHERE user_id = userid and pname = portfolioname;
    
    OPEN cur;
    
	getTotalValue: LOOP
    
		FETCH cur INTO stock_ticker_in;
        
        IF done = 1 
			THEN LEAVE getTotalValue;
		END IF;
        
        SELECT quantity_stock_till_date(date_in, pid_in, stock_ticker_in) into quant;
        SELECT quantity_stock_price_date(date_in, stock_ticker_in) into value;
        
        SET total_value = total_value + (quant * value);
    
    END LOOP getTotalValue;
    
    SELECT total_value as 'value';
END //
DELIMITER ;

-- call get_portfolio_value_on_a_date(1, "p1", "2022-09-09");


-- composition on a date
DROP PROCEDURE IF EXISTS get_portfolio_cost_basis;
DELIMITER //
CREATE PROCEDURE get_portfolio_cost_basis(
	userid INT,
    portfolioname VARCHAR(20), 
    date_in DATE
)
BEGIN
	DECLARE done INT DEFAULT 0;
    DECLARE total DOUBLE DEFAULT 0;
    DECLARE quant DOUBLE DEFAULT 0;
    
    DECLARE pid_in INT;
    DECLARE stock_ticker_in VARCHAR(20);
    
	DECLARE cur CURSOR FOR 
		SELECT DISTINCT(stock_ticker)
        FROM transaction
        WHERE pid = (
			SELECT pid 
			FROM portfolio
			WHERE user_id = userid and pname = portfolioname
		);
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    SELECT pid into pid_in
	FROM portfolio
	WHERE user_id = userid and pname = portfolioname;
    
    open cur;
    
    getTotalValue: LOOP
    
		FETCH cur INTO stock_ticker_in;
        
        IF done = 1 
			THEN LEAVE getTotalValue;
		END IF;
        
        SELECT money_invested_till_date(date_in, pid_in, stock_ticker_in) into quant;
        
        SET total = total + quant;
    
    END LOOP getTotalValue;
    
    SELECT total as 'cost_basis';
END //
DELIMITER ;

-- call get_portfolio_cost_basis(1, "p1", "2022-10-10");
-- call get_portfolio_cost_basis(1, "p1", "2023-10-10");


-- get profile details
DROP PROCEDURE IF EXISTS get_profile_details;
DELIMITER //
CREATE PROCEDURE get_profile_details(
	userid INT
)
BEGIN
	SELECT nick_name, phone_number, password
    FROM user
    where id = userid;
END //
DELIMITER ;


-- get stock price dates
DROP PROCEDURE IF EXISTS get_stock_price_dates;
DELIMITER //
CREATE PROCEDURE get_stock_price_dates(
	stock VARCHAR(20),
    startdate DATE,
    enddate DATE
)
BEGIN
	SELECT date as date, close as price
    FROM stock_data
    where stock_ticker = stock AND date >= startdate AND date <= enddate;
END //
DELIMITER ;



-- get all brokers
DROP PROCEDURE IF EXISTS get_all_brokers;
DELIMITER //
CREATE PROCEDURE get_all_brokers()
BEGIN
	SELECT broker_name
    FROM brokerage_firm;
END //
DELIMITER ;



DROP PROCEDURE IF EXISTS get_brokers_for_user;
DELIMITER //
CREATE PROCEDURE get_brokers_for_user(
	user_id INT
)
BEGIN
	SELECT broker_name, commission
    FROM brokerage_firm bf JOIN user_broker_element ue ON bf.bid = ue.bid
    where ue.uid = user_id;
END //
DELIMITER ;