use portfolio_manager;


DROP PROCEDURE IF EXISTS run_strategy_date;
DELIMITER //
CREATE PROCEDURE run_strategy_date(
	strat_id INT,
    date_in DATE,
    amount DOUBLE,
    comm DOUBLE,
    pidin INT
) 
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE stock VARCHAR(20);
    DECLARE weight DOUBLE;
    DECLARE price DOUBLE;
    DECLARE ind_amount DOUBLE;
    DECLARE temp_d DATE;
    DECLARE count_t INT DEFAULT 0;
    DECLARE stock_count DOUBLE;
    
	DECLARE cur CURSOR FOR 
		SELECT stock_ticker, weightage
        FROM strategy_element
        WHERE sid = strat_id;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    OPEN cur;
    
    getStratElements : LOOP
        FETCH cur into stock, weight;
        
		SET temp_d = date_in;
        
		IF done = 1 
			THEN LEAVE getStratElements;
		END IF;
        
        SET ind_amount = (weight / 100) * amount - comm;
        
        helperLoop : LOOP
			SELECT count(*) INTO count_t
			FROM stock_data
			WHERE stock_ticker = stock and date = temp_d;
            
            IF (count_t > 0) 
				THEN SELECT close INTO price
				FROM stock_data
				WHERE stock_ticker = stock and date = temp_d;
                
                SET stock_count = ind_amount / price;
                
                call insert_new_transaction(stock, pidin, "BUY", stock_count, price, temp_d, comm);
                
                
                LEAVE helperLoop;
                
				
            ELSE 
				SET temp_d = DATE_ADD(temp_d, INTERVAL 1 day);
            
            END IF;
        END LOOP helperLoop;

    END LOOP getStratElements;
END //
DELIMITER ;



-- RUN STRATEGY
DROP PROCEDURE IF EXISTS run_strategy;
DELIMITER //
CREATE PROCEDURE run_strategy(
	strat_id INT
)
BEGIN
	DECLARE start_d DATE;
	DECLARE end_d DATE;
    DECLARE temp_d DATE;
    DECLARE freq DOUBLE;
	DECLARE amountin DOUBLE;
    DECLARE comm DOUBLE;
    DECLARE pidin INT;
    
    SELECT start_date, end_date, frequency, amount, commission, pid
    INTO start_d, end_d, freq, amountin, comm, pidin
    FROM strategy
    WHERE sid = strat_id;
    
    SET temp_d = start_d;
    
    WHILE start_d <= end_d DO
		call run_strategy_date(strat_id, temp_d, amountin, comm, pidin);
        
        SET start_d = DATE_ADD(start_d, INTERVAL freq day);
        SET temp_d = start_d;
    END WHILE;
    
END//
DELIMITER ;



-- delete portfolio 
DROP PROCEDURE IF EXISTS delete_portfolio;
DELIMITER //
CREATE PROCEDURE delete_portfolio(
	userid INT, 
    portfolioname VARCHAR(20)
)
BEGIN
	DECLARE pid_in INT;
    
    SELECT pid into pid_in
    FROM portfolio
    WHERE user_id = userid AND pname = portfolioname;
    
    DELETE FROM portfolio
    WHERE pid = pid_in;
END //
DELIMITER ;



-- update_profile
DROP PROCEDURE IF EXISTS update_profile_details;
DELIMITER //
CREATE PROCEDURE update_profile_details(
	userid INT,
    nick_namein VARCHAR(20), 
    phone_numberin VARCHAR(10),
    passwordin varchar(256)
)
BEGIN
	UPDATE user
    SET nick_name = nick_namein, phone_number = phone_numberin, password = passwordin
    where id = userid;
END //
DELIMITER ;