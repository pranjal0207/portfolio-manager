DROP DATABASE IF EXISTS portfolio_manager;
CREATE DATABASE portfolio_manager;

USE portfolio_manager;

CREATE TABLE user (
	id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password varchar(256) NOT NULL,
    nick_name VARCHAR(50), 
    phone_number VARCHAR(10),
    PRIMARY KEY (id),
    unique(username)
);

CREATE TABLE portfolio (
	pid INT NOT NULL AUTO_INCREMENT,
    pname VARCHAR(20) NOT NULL,
    user_id INT NOT NULL,
    date_of_creation DATE NOT NULL,
    PRIMARY KEY (pid),
    UNIQUE(user_id, pname),
    CONSTRAINT `portfolio_fk` FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE exchange (
    ex_notation VARCHAR(10) NOT NULL,
    ex_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (ex_notation)
);

CREATE TABLE stock (
	stock_ticker VARCHAR(20) NOT NULL,
    stock_name VARCHAR(200) NOT NULL,
    exchange_id VARCHAR(20) NOT NULL,
    ipo_date DATE NOT NULL,
    status VARCHAR(10) NOT NULL,
    PRIMARY KEY (stock_ticker),
    CONSTRAINT `stock_fk` FOREIGN KEY (exchange_id) REFERENCES exchange (ex_notation)
);

CREATE TABLE transaction (
	tid INT NOT NULL AUTO_INCREMENT,
    stock_ticker VARCHAR(20) NOT NULL,
    pid INT NOT NULL,
    transaction_type VARCHAR(5),
    quantity DOUBLE NOT NULL,
    price DOUBLE NOT NULL,
    transaction_date DATE NOT NULL,
    commission DOUBLE NOT NULL,
    PRIMARY KEY (tid),
    CONSTRAINT `transaction_fk1` FOREIGN KEY (stock_ticker) REFERENCES stock (stock_ticker),
    CONSTRAINT `transaction_fk2` FOREIGN KEY (pid) REFERENCES portfolio (pid),
    CHECK (quantity > 0),
    CHECK (commission >= 0),
    CHECK (price > 0)
);

CREATE TABLE strategy(
	sid INT NOT NULL AUTO_INCREMENT,
    sname VARCHAR(20) NOT NULL,
    pid INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    amount DOUBLE NOT NULL,
    commission DOUBLE NOT NULL,
    frequency DOUBLE NOT NULL,
    PRIMARY KEY (sid),
    CONSTRAINT `strategy_fk` FOREIGN KEY (pid) REFERENCES portfolio (pid),
    UNIQUE (pid, sname),
    CHECK (amount > 0),
    CHECK (commission >= 0),
    CHECK (start_date <= end_date),
    CHECK (frequency >= 0)
);

CREATE TABLE strategy_element(
	seid INT NOT NULL AUTO_INCREMENT,
	sid INT NOT NULL,
    stock_ticker VARCHAR(20) NOT NULL,
    weightage DOUBLE NOT NULL,
    PRIMARY KEY (seid),
    UNIQUE (sid, stock_ticker),
    CONSTRAINT `strategy_element_fk1` FOREIGN KEY (sid) REFERENCES strategy (sid),
    CONSTRAINT `strategy_element_fk2` FOREIGN KEY (stock_ticker) REFERENCES stock (stock_ticker),
    check (weightage >= 0 and weightage <= 100)
);

CREATE TABLE stock_data(	
	stock_ticker VARCHAR(20) NOT NULL,	
	date DATE NOT NULL,
	open DOUBLE NOT NULL,
	close DOUBLE NOT NULL,
	low DOUBLE NOT NULL,
	high DOUBLE NOT NULL,
    volume DOUBLE NOT NULL,
	PRIMARY KEY (stock_ticker, date),
    CONSTRAINT `stock_data_fk2` FOREIGN KEY (stock_ticker) REFERENCES stock (stock_ticker),
    CHECK (open > 0),
    CHECK (close > 0),
    CHECK (low > 0),
    CHECK (high > 0),
    CHECK (volume > 0)
);

CREATE TABLE brokerage_firm(
	bid INT NOT NULL AUTO_INCREMENT,
    broker_name VARCHAR(50) NOT NULL,
    commission DOUBLE NOT NULL,
    PRIMARY KEY (bid),
    UNIQUE(broker_name),
    CHECK (commission >= 0)
);


CREATE TABLE user_broker_element(
	bid INT NOT NULL,
    uid INT NOT NULL,
    PRIMARY KEY (bid, uid),
    CONSTRAINT `user_broker_element_fk1` FOREIGN KEY (bid) REFERENCES brokerage_firm (bid),
    CONSTRAINT `user_broker_element_fk2` FOREIGN KEY (uid) REFERENCES user (id)
);

INSERT INTO exchange VALUES ("NASDAQ", "National Association of Securities Dealers Automated Quotations");
INSERT INTO exchange VALUES ("NYSE", "The New York Stock Exchange");
INSERT INTO exchange VALUES ("NYSE MKT", "The New York Stock Exchange (American)");
INSERT INTO exchange VALUES ("NYSE ARCA", "The New York Stock Exchange (Archipelago Exchange)");
INSERT INTO exchange VALUES ("BATS", "Better Alternative Trading System");


INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("Goldman Sachs", 8);
INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("Charles Schwab", 5);
INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("Fidelity Investments", 9);
INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("Bank of America", 10);
INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("J.P. Morgan", 10);
INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("Robinhood", 4);
INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("Coinbase", 6);
INSERT INTO brokerage_firm(broker_name, commission)
VALUES ("Ally Invest", 5);