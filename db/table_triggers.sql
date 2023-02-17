USE portfolio_manager;

DROP TRIGGER IF EXISTS delete_strategy_element;
DELIMITER //
CREATE TRIGGER delete_strategy_element
BEFORE DELETE ON strategy
FOR EACH ROW
BEGIN
	DELETE FROM strategy_element
    WHERE sid = OLD.sid;
END //
DELIMITER ;



DROP TRIGGER IF EXISTS delete_portfolio;
DELIMITER //
CREATE TRIGGER delete_portfolio
BEFORE DELETE ON portfolio
FOR EACH ROW
BEGIN
	DELETE FROM transaction
    WHERE pid = OLD.pid;
    
    DELETE FROM strategy
    where pid = OLD.pid;
END //
DELIMITER ;