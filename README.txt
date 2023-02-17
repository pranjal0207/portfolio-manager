Functionalities working in the code:
1) Create a new User -> Creates a new user with unique IDs. Initial user's id starts from 1000.

2) Login existing user -> Input the userId to access data of a particular existing user.

3) Create a new flexible portfolio.
    i) Create manually by specifying each stock.
    ii) Create portfolio by specifying a strategy.
    iii) Upload portfolio using an XML file.

4) The ability to modify stocks by specifying the number of shares, and date (with or without commission fees) for both buy and sell.

5) The ability to persist all the user and stock data.

6) Store all the stock data in files to avoid redundant API calls.

6) Download a portfolio.Allow user to download his portfolio to view it in a readable manner.

7) Ability to specify weight of each stock to be invested in a portfolio for a given amount at various dates by specifying a frequency.

8) Ability to query cost basis and value of portfolio at particular dates with strategy applied to a portfolio.

9) Add a new dollar cost averaging strategy to an existing portfolio.

10) GUI to select a date or choose a file to upload using date anf file pickers.

11) Performance of a portfolio displayed as a bar graph in the GUI.


Our program allows the following features:
-> User need not enter ticker symbol and can choose the tickers from the dropdown.
-> Stock prices are calculated on closing day.
-> System support all ACTIVE stocks available from the AlphaVantageAPI(https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo).
-> System supports all dates for which the data is available from the AlphaVantageAPI.
-> A stock in a portfolio can have 0 quantity.
-> Sell is allowed in the stock input as long as the overall stock quantity remains positive.


--------------------CONSOLE BASED FUNCTIONALITIES---------------------------------------------
Functionalities working in the code:

1) Create a new User -> Creates a new user with unique IDs. Initial user's id starts from 1000.

2) Login existing user -> Input the userId to access data of a particular existing user.

3) Add a new portfolio
	3.a) Create Inflexible Portfolio
	    -> Manually input stocks, date and quantity for a portfolio.
	    -> Upload an XML file with all data to create a new portfolio.
	    -> Consolidate same stocks together in a portfolio, if multiple entries detected.
	3.b) Create Flexible Portfolio
		-> Manually input stocks, date, quantity and commission for the portfolio.
		-> Upload an XML file with all data to create a new portfolio.
		-> Consolidate same stocks together in a portfolio, if multiple entries detected.

4) View a portfolio as of a given date
	3.a) View a Flexible portfolio given a date.
	3.b) View a Inflexlibe portfolio given a date.
		->View a portfolio gives the composition as per the given date by the user. 
		If a stock was not available on that particular date and was added later it will not be shown in the composition as expected.

5) Determine value of a portfolio on a Date 
	3.a) Determine Value of Flexible portfolio given a date
	3.b) Determine Value of Inflexible portfolio given a date.
		-> Get the combined value of all the stocks in a portfolio on a particular date when market was open.

6) Modify a flexible portfolio
	-> Modify any flexible portfolio to add or sell stocks.
	-> No modify option is provided for Inflexible portfolios.

7) Analyze cost basis for a portfolio.
	7.a) Analyze cost basis for Flexible Portfolio.
		-> Flexible Portfolio has commission as well which is included in the cost basis calculation.
	7.b) Analyze cost basis for Inflexible Portfolio.
		-> Inflexible Portfolio does not have commission hence it is not included in the cost basis calculation.

8) Show performance of a portfolio over a time period.
	8.a) Show flexible portfolio performance.
	8.b) Show inflexible portfolio performance.
		-> The performance of a portfolio is displayed as a chart plot with minimum of 5 entries and a maximum of 30 entries thus keeping it within given constraints.
		-> The chart has a maximum of 50 asterik (*) representing the value of the portfolio for a given date.
		-> The scale of the chart is relative to allow huge variations in the values and is mentioned at the bottom of the chart.

9) Automatically save the user and portfolio data.
	-> Automatically save user data along with all its flexible and inflexible portfolios.

10) Caching of data from AlphaVantage API into local storage system for lower API hit rate.

11) Re-populate the data from XML files back, when the program is turned on again after shut down.

12) Download a portfolio 
	-> Allow user to download his portfolio to view it in a readable manner. Works for both types of portfolios.


Our program allows the following features:
-> User can enter ticker symbols in lowercase as well.
-> Stock prices are calculated on closing day.
-> System support all ACTIVE stocks available from the AlphaVantageAPI(https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo).
-> System supports all dates for which the data is available from the AlphaVantageAPI.
-> A portfolio can be created without any stocks.
-> A stock in a portfolio can have 0 quantity.
-> Negative quantity (sell) is allowed in the stock input as long as the overall stock quantity remains positive.
