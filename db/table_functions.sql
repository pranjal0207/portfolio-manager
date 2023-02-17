USE portfolio_manager;

-- get quantity of stock till date
DROP FUNCTION IF EXISTS quantity_stock_till_date;
DELIMITER //
CREATE FUNCTION quantity_stock_till_date(
	date_in DATE,
    pid_in INT,
    stock_ticker_in VARCHAR(20)
)
RETURNS DOUBLE deterministic reads sql data
BEGIN
    DECLARE done INT DEFAULT 0;
    
	DECLARE quant DOUBLE DEFAULT 0;
    DECLARE trans_quant DOUBLE;
    DECLARE trans_type VARCHAR(20);
    
    DECLARE cur CURSOR FOR 
		SELECT quantity, transaction_type
        FROM TRANSACTION
        WHERE transaction_date <= date_in AND pid = pid_in AND stock_ticker = stock_ticker_in
        ORDER BY transaction_date;
        
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    OPEN cur;
    
	getQuant: LOOP
		FETCH cur into trans_quant, trans_type;
        
        IF done = 1 
			THEN LEAVE getQuant;
		END IF;
        
        IF trans_type = "BUY"
			 THEN SET quant = quant + trans_quant; 
		END IF;
        
        IF trans_type = "SELL"
            THEN SET quant = quant - trans_quant; 
		END IF;
        
        
    END LOOP getQuant;
    
    RETURN quant;
END //
DELIMITER ;



-- get stock price on date
DROP FUNCTION IF EXISTS quantity_stock_price_date;
DELIMITER //
CREATE FUNCTION quantity_stock_price_date(
	date_in DATE,
    stock_ticker_in VARCHAR(20)
)
RETURNS DOUBLE deterministic reads sql data
BEGIN
	DECLARE value DOUBLE;

	SELECT close into value
    FROM stock_data
    WHERE date = date_in AND stock_ticker = stock_ticker_in;
    
    return value;
END //
DELIMITER ;



-- get money invested in a stock
DROP FUNCTION IF EXISTS money_invested_till_date;
DELIMITER //
CREATE FUNCTION money_invested_till_date(
	date_in DATE,
    pid_in INT,
    stock_ticker_in VARCHAR(20)
)
RETURNS DOUBLE deterministic reads sql data
BEGIN
	DECLARE done INT DEFAULT 0;
    DECLARE total_value DOUBLE DEFAULT 0;
    
    DECLARE trans_quant DOUBLE;
    DECLARE trans_type VARCHAR(20);
	DECLARE amount DOUBLE;
	DECLARE com DOUBLE;
    
    DECLARE cur CURSOR FOR 
		SELECT quantity, transaction_type, price, commission
        FROM TRANSACTION
        WHERE transaction_date <= date_in AND pid = pid_in AND stock_ticker = stock_ticker_in
        ORDER BY transaction_date;
        
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    OPEN cur;
    
    getInvestment: LOOP
		FETCH cur into trans_quant, trans_type, amount, com;
        
        IF done = 1 
			THEN LEAVE getInvestment;
		END IF;
        
        IF trans_type = "BUY"
			THEN SET total_value = total_value + (trans_quant * amount) + com; 
		END IF;
        
        IF trans_type = "SELL"
            THEN SET total_value = total_value + com;
		END IF;
        
    END LOOP getInvestment;
    
    return total_value;
END //
DELIMITER ;


-- get stock avg price till date
DROP FUNCTION IF EXISTS stock_avg_till_date;
DELIMITER //
CREATE FUNCTION stock_avg_till_date(
	pidin INT,
	stock VARCHAR(20),
    date_in DATE 
)
RETURNS DOUBLE deterministic reads sql data
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE avg_price DOUBLE DEFAULT 0;
	DECLARE total DOUBLE DEFAULT 0;
	DECLARE quant_t DOUBLE DEFAULT 0;
	DECLARE type VARCHAR(5);
	DECLARE pricein DOUBLE;
	DECLARE quant DOUBLE;
    
    DECLARE cur CURSOR FOR
		SELECT price, transaction_type, quantity
        FROM transaction
        WHERE pid = pidin AND stock_ticker = stock AND transaction_date <= date_in;
        
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
	open cur;
    
    getAvgPrice : LOOP
		FETCH cur INTO pricein, type, quant;
        
        IF done = 1 
			THEN LEAVE getAvgPrice;
		END IF;
        
        IF type = "BUY" THEN
			SET total = total + (pricein * quant);
            SET quant_t = quant_t + quant;
        END IF;
        
        IF type = "SELL" THEN
			SET total = total - (pricein * quant);
            SET quant_t = quant_t - quant;
        END IF;
        
	END LOOP getAvgPrice;
    
    SET avg_price = total / quant_t;
    
    return avg_price;
END //
DELIMITER ;