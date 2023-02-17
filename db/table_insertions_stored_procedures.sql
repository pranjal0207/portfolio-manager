USE portfolio_manager;

-- Inserting into user table
DROP PROCEDURE IF EXISTS insert_new_user;
DELIMITER //
CREATE PROCEDURE insert_new_user(
    usernamein VARCHAR(20), 
    password VARCHAR(256)
)
BEGIN
	DECLARE EXIT HANDLER FOR 1062
    BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Username should be unique. Please try again!";
    END;
    
    INSERT INTO user (username, password) 
    VALUES (usernamein, password);
    
    SELECT id
    FROM USER
    WHERE username = usernamein;
END//
DELIMITER ;



-- Inserting into portfolio table
DROP PROCEDURE IF EXISTS insert_new_portfolio;
DELIMITER //
CREATE PROCEDURE insert_new_portfolio(
    pnamein VARCHAR(20), 
    user_idin INT,
    date_of_creation_in DATE
)
BEGIN
	DECLARE EXIT HANDLER FOR 1452
    BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "User ID invalid or not available. Please try again!";
    END;
    
    DECLARE EXIT HANDLER FOR 1062
    BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Duplicate portfolio name for current user. Please try again!";
    END;

    INSERT INTO portfolio (pname, user_id, date_of_creation) 
    VALUES (pnamein, user_idin, date_of_creation_in);
    
    SELECT pid
    FROM portfolio 
    WHERE pname = pnamein and user_id = user_idin;
END//
DELIMITER ;



-- Inserting into exchange table
DROP PROCEDURE IF EXISTS insert_new_exchange;
DELIMITER //
CREATE PROCEDURE insert_new_exchange(
    ex_notation VARCHAR(10), 
    ex_name VARCHAR(20)
)
BEGIN
	DECLARE EXIT HANDLER FOR 1062
    BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Exchange notation not unique. Please try again!";
    END;

    INSERT INTO exchange
    VALUES (ex_notation, ex_name);
END//
DELIMITER ;



-- Insert into stocks table
DROP PROCEDURE IF EXISTS insert_new_stock;
DELIMITER //
CREATE PROCEDURE insert_new_stock(
    stock_ticker VARCHAR(20), 
    stock_name VARCHAR(500),
    exchange_id VARCHAR(20),
    ipo_date DATE,
    status VARCHAR(10)
)
BEGIN
	DECLARE EXIT HANDLER FOR 1062
    BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Stock ticker not unique. Please try again!";
	END;

	DECLARE EXIT HANDLER FOR 1452
	BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Exchange notation invalid or not available. Please try again!";
    END;
    
    INSERT INTO stock
    VALUES (stock_ticker, stock_name, exchange_id, ipo_date, status);
END//
DELIMITER ;



-- Insert into transaction table
DROP PROCEDURE IF EXISTS insert_new_transaction;
DELIMITER //
CREATE PROCEDURE insert_new_transaction(
	stock_tickerin VARCHAR(20),
    pidin INT,
    transaction_typein VARCHAR(5),
    quantityin DOUBLE,
    price DOUBLE,
    transaction_date DATE,
    commission DOUBLE
)
BEGIN
	DECLARE peidin INT;
    DECLARE quant DOUBLE;
	DECLARE sys_Date DATE;
    
    DECLARE EXIT HANDLER FOR 3819
	BEGIN
        IF quantityin < 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Quantity is less than or equal to zero. Please try again!";
        END IF;
        IF price < 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Price is less than or equal to zero. Please try again!";
        END IF;
        IF commission < 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Commission is less than or equal to zero. Please try again!";
        END IF;
    END;

	SELECT SYSDATE() into sys_Date;
    
    IF (sys_Date <= transaction_date)
		THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Stock cannot be bought/sold for a future date. Please try again!";
	END IF;
    
    SELECT quantity_stock_till_date(transaction_date, pidin, stock_tickerin) into quant;
    
    IF (quantityin > quant AND transaction_typein = "SELL")
		THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Quantity not available for selling. Please try again!";
	END IF;
    
    INSERT INTO transaction(stock_ticker, pid, transaction_type, quantity, price, transaction_date, commission)
	VALUES (stock_tickerin, pidin, transaction_typein, quantityin, price, transaction_date, commission);
END//
DELIMITER ;



-- Insert into strategy table
DROP PROCEDURE IF EXISTS insert_new_strategy;
DELIMITER //
CREATE PROCEDURE insert_new_strategy(
	snamein VARCHAR(20),
    pidin INT,
    start_date DATE,
    end_date DATE,
    amount DOUBLE,
    commission DOUBLE,
    frequencyin DOUBLE
)
BEGIN
	DECLARE EXIT HANDLER FOR 1452
    BEGIN
		DECLARE count_portfolio INT;
			
		SELECT count(*) into count_portfolio FROM portfolio 
			WHERE pid = pidin;
		
		IF count_portfolio = 0
            THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Portfolio id is invalid or not present. Please try again!";
		END IF;
    END;
    
	DECLARE EXIT HANDLER FOR 1062
    BEGIN
		DECLARE count_t INT;
        
        SELECT count(*) into count_t FROM strategy 
        WHERE sname = snamein AND pid = pidin;
        
        IF count_t > 0 
            THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Duplicate portfolio id and strategy name encountered. Please try again!";
        END IF;
    END;
    
    DECLARE EXIT HANDLER FOR 3819
	BEGIN
        IF amount <= 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Amount is less than or equal to zero. Please try again!";
        END IF;
        IF commission <= 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Commission is less than or equal to zero. Please try again!";
        END IF;
        IF frequencyin <= 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Frequency is less than or equal to zero. Please try again!";
        END IF;
        IF start_date > end_date
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "End date is smaller than start date. Please try again!";
        END IF;
    END;	
    
    INSERT INTO strategy(sname, pid, start_date, end_date, amount, commission, frequency)
    VALUES (snamein, pidin, start_date, end_date, amount, commission, frequencyin);
    
    SELECT sid
    FROM strategy
    WHERE pid = pidin AND sname = snamein;
END//
DELIMITER ;



-- Insert into strategy_element table
DROP PROCEDURE IF EXISTS insert_new_strategy_element;
DELIMITER //
CREATE PROCEDURE insert_new_strategy_element(
	sidin INT,
    stock_tickerin VARCHAR(20),
    weightage DOUBLE
)
BEGIN
	DECLARE EXIT HANDLER FOR 1452
    BEGIN
		DECLARE count_stock INT;
        DECLARE count_strategy INT;
        
		SELECT count(*) into count_strategy FROM strategy 
			WHERE sid = sidin;
            
		SELECT count(*) into count_stock FROM stock 
			WHERE stock_ticker = stock_tickerin;
		
		IF count_stock = 0
            THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Stock ticker is invalid or not present. Please try again!";
		END IF;
        
        IF count_strategy = 0
            THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Strategy ID is invalid or not present. Please try again!";
		END IF;
    END;
    
    DECLARE EXIT HANDLER FOR 1062
    BEGIN
		DECLARE count_t INT;
        
        SELECT count(*) into count_t FROM strategy_element
        WHERE stock_ticker = stock_tickerin AND sid = sidin;
        
        IF count_t > 0 
            THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Duplicate strategy id and stock ticker encountered. Please try again!";
        END IF;
    END;
    
    DECLARE EXIT HANDLER FOR 3819
	BEGIN
        IF weightage <= 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Weightage is less than or equal to zero. Please try again!";
        END IF;
        IF weightage > 100 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Weightage is greater than hundred. Please try again!";
        END IF;
    END;
    
    INSERT INTO strategy_element(sid, stock_ticker, weightage)
    VALUES (sidin, stock_tickerin, weightage);
END//
DELIMITER ;



-- Insert into strategy_element table
DROP PROCEDURE IF EXISTS insert_new_stock_data;
DELIMITER //
CREATE PROCEDURE insert_new_stock_data(
	stock_tickerin VARCHAR(20),
    datein DATE,
	openin DOUBLE,
	closein DOUBLE,
	lowin DOUBLE,
	highin DOUBLE,
    volumein DOUBLE
)
BEGIN
	DECLARE EXIT HANDLER FOR 1062
    BEGIN
		UPDATE stock_data
        SET stock_ticker = stock_tickerin, date = datein, open = openin, close = closein, low = lowin, 
			high = highin, volume = volumein
        WHERE stock_ticker = stock_tickerin AND date = datein;
    END;
    
    DECLARE EXIT HANDLER FOR 1452
    BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Stock ticker invalid or not present. Please try again!";
    END;
    
    DECLARE EXIT HANDLER FOR 3819
	BEGIN
        IF openin < 0 
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Open price is less than zero. Please try again!";
        END IF;
        IF closein < 0  
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Close price is less than zero. Please try again!";
		END IF;
        IF lowin < 0  
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Low price is less than zero. Please try again!";
		END IF;
        IF highin < 0  
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "High price is less than zero. Please try again!";
		END IF;
        IF volumein < 0  
			THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Volume is less than zero. Please try again!";
		END IF;
    END;
	
    INSERT INTO stock_data
    VALUES (stock_tickerin, datein, openin, closein, lowin, highin, volumein);
END//
DELIMITER ;


-- Insert new brokers 
DROP PROCEDURE IF EXISTS add_broker;
DELIMITER //
CREATE PROCEDURE add_broker(
	userid INT, 
    brokername VARCHAR(50)
)
BEGIN
	DECLARE id INT DEFAULT 0;
    
	DECLARE EXIT HANDLER FOR 1452
    BEGIN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Invalid broker name. Please try again!";
    END;
    
	SELECT bid into id 
    FROM brokerage_firm
    WHERE broker_name = brokername;

    
	INSERT INTO user_broker_element
    VALUES (id, userid);
END //
DELIMITER ;