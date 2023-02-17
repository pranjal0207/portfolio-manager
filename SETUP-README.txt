1) Execute the stocks.jar file using the command "java -jar stocks.jar gui". Giving gui in run time argument will initiate a system with the GUI. You can
remove the argument gui to run the command line based program, steps for which are given in the second half of readme.

2) For first time user, you can create a user by putting an alphanumeric username. On success it will redirect you to another page.
Please keep a note of your user id(shown in parentheses). You can use it later to log back in and see your portfolios.

3) Click on create portfolio to create a new portfolio. Select one of the three options from the dropdown in the popup menu.
    i) Create portfolio manually.
    ii) Create portfolio directly using strategy
    iii) Upload portfolio via XML file.

3.i) 1) On selecting manual portfolio creation, a popup will open. You can input a new portfolio name. Minimum one stock addition is required, you can select
        an available stock ticker from drop down menu. You can also type the ticker name for drop down to regex match the starting characters and show you
        the ticker.

     2) Input the commission (default 0) for transaction and the quantity bought initially. Quantity cannot be negative as shorting of stocks is not allowed.

     3) Select date by clicking on the ... box near the date box. It will open a date picker option.Select the date you bought a particular stock.
        Long press year change arrows to quickly go to years in very past.

     4) To add another stock, click on add stock button. This will add more input boxes to add details for stock bought.Repeat the above steps.
        When done click on create portfolio button.

3.ii) 1) On selecting creating a portfolio directly using a strategy, a popup will open. Enter the portfolio name. Choose the strategy you want to apply.
         Currently only Dollar Cost Averaging is supported. Input a name for strategy.

      2) Enter the amount you would like to invest and commission for each transaction(default 0).

      3) Enter the start date. Choose date by clicking on the date picker option from ... button.
         Specify an end date or keep it blank for a never ending strategy.

      4) Specify the frequency of investment by inputting after how many days should a new investment be made.

      5) Select stock tickers from drop down and specify the percent weightage of each stock. Add more stocks by clicking on add another stock button.
         Total weightage of stocks must exactly be 100%.

      6) Click create portfolio button. This will create a portfolio and apply all the transactions created by the strategy till now.

3.iii) 1) On selecting creation of portfolio via XML file, it would open a popup. Input the portfolio name.

       2) From the file picker migrate to the xml file where portfolio details are stored and select the file.

       3) XML file format :
                                                            <PortfolioModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>ticker_symbol</StockTicker>
                                                                <totalQuantity>Quantity</totalQuantity>
                                                                <Date>YYYY-MM-DD</Date>
                                                                <BrokerCommission>Broker_Commission</BrokerCommission>
                                                              </PortfolioElementModel>
                                                            </PortfolioModel
         Example :
                                                            <PortfolioModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>GOOG</StockTicker>
                                                                <totalQuantity>100</totalQuantity>
                                                                <Date>2022-09-22</Date>
                                                                <BrokerCommission> 50.9</BrokerCommission>
                                                              </PortfolioElementModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>MSFT</StockTicker>
                                                                <totalQuantity>10</totalQuantity>
                                                                <Date>2022-09-15</Date>
                                                                <BrokerCommission>70.0</BrokerCommission>
                                                              </PortfolioElementModel>
                                                            </PortfolioModel>
       4) Click open to create the portfolio.

4) On creation of portfolio, you will now see the portfolio created with various options next to it.
      i) Analyze
      ii) Add Strategy
      iii) Modify
      iv) Download

4.i) 1) On analyze a portfolio, you will be redirected to another screen where you will see the current composition of portfolio at current date.

     2) You can select from buttons on top to do different analysis on the selected portfolio.
        i) Click on show composition and select a date. It will show you what the composition of portfolio was up till that date.
           Press OK.

        ii) Show performance chart of the portfolio. Select the start and end date for graphs to be created.
            Press OK.

        iii) Show cost basis of portfolio and select a date. It will show you what the cost basis of portfolio was up till that date.
             Press OK.

        iv) Get value on a date of portfolio and select a date. It will show you what the value of portfolio was up till that date.
            Press OK.

        v) Press back to go back to portfolio selection menu.

4.ii) 1) Selecting add strategy to add a new strategy to an existing portfolio.

      2) Replicate steps from 3.ii) to create a strategy and press save strategy.

4.iii) 1)  Modify a portfolio option will give option to manually buy or sell a stock.

       2)  Select the stock ticker and specify a buy/sell. Select date, quantity and commission of transaction as well.
           Ensure the quantity specified does not result in an overall shorting.

       3) Click save to modify portfolio and save transaction.

4.iv) 1) Download portfolio will save portfolio to user's system and display the path where it is saved.

5) Logout button can be used to logout and login another user by inputting the userid.

EXTERNAL JAR : We have used only 1 external jar for building the date picker. It is free to use library. The licence and usage agreement can be found on:
https://github.com/JDatePicker/JDatePicker
If you want to run code using IntelliJ, use the external jar (jdatepicker-1.3.4) from submission package and follow the following steps:
Go to File-> Project Structure-> Modules -> Dependencies -> Click +, add external jar.


-------------------------------------------------README FOR RUNNING CONSOLE BASED PROGRAM------------------------------------------------------------------------------------------
1) Make sure the folder from where you are running the stocks.jar file has read-write access as our program will read/write xml files to store the data.

2) Ensure the directory where JAR file is located also has a UserSetModel and StockSetModel folder. This UserSetModel folder should contain a UserSetModel.xml.
   Do not modify the contents of this folder. This folder will be created when you run the program for the first time.

3) Execute the stocks.jar file. The console should show options to Create/Login user and Quit.

4) Enter the number for the option you want to choose. Press 1 and then enter to create user.

5) Please enter username as a alphanumeric string and then press enter. It should display a welcome message with your username.
   It will also show you the userId generated for you. Please keep a note of it, as it will be used to login back later in future.

6) Choose one of the options displayed by entering the number[1-8] and then press enter. For first time, press 1 to add new portfolio.

7) You will be asked to create either a flexible or inflexible portfolio. Choose the one you want. Flexible portfolios can be modified later, however, inflexible portfolios cannot.

8) Please enter portfolio name as a alphanumeric string and then press enter. It will display options to enter data manually or upload via XML file.

9) Press 1 and enter. This will enable us to manually enter all the details in the console.

10) Input number of stocks to be entered in this portfolio. Input 3 (entering total of 3 stocks in portfolio).

11) Input the stock ticker symbol for the first stock. We support all the stocks available from the AlphaVantageAPI.
    The list of stocks can be found from their free API call - https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo

12) Enter the date in the displayed format. We support all dates for which data is provided by the AlphaVantageAPI.
    Please ensure you enter a date which does not fall when market is closed, i.e. weekend or public holiday.
    It will show you a prompt if you do so to reenter correct date.

13) Enter the quantity of stocks. If you want to 'sell' the stock please enter a negative quantity. Press enter.
    Note - The system will allow selling of stocks as long as the total quantity of the stock in that portfolio is non-negative.

14) If you are creating a flexible portfolio, you will be asked to enter the commission as well.
    The commission here is absolute total commission for the trade.

15) Repeat process for the rest of two stocks as well.

16) Enter 2 and enter to view a portfolio.

16) Enter the appropriate number next to the portfolio name to view and then press enter.

17) Enter the date on which you would like to see the composition of the portfolio. The system will show the composition of the portfolio as of that date. This works for both flexible and inflexible portfolios.

18) Input 1 and press enter again to insert a new portfolio.

19) Input a portfolio type, portfolio name and press enter. To load data from XML file,Input 2 and press enter.

20) Please ensure the XML file being uploaded is in the same folder from where the JAR is being run.
    Every stock entry requires ticker_symbol to uniquely identify the stock, quantity of stock bought/sold
    and the transaction date of the stock.
    Refer this format for adding Stocks to the portfolio :
        For Inflexible Portfolio:
                                                            <PortfolioModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>ticker_symbol</StockTicker>
                                                                <totalQuantity>Quantity</totalQuantity>
                                                                <Date>YYYY-MM-DD</Date>
                                                              </PortfolioElementModel>
                                                            </PortfolioModel>
        For Flexible Portfolio:
                                                            <PortfolioModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>ticker_symbol</StockTicker>
                                                                <totalQuantity>Quantity</totalQuantity>
                                                                <Date>YYYY-MM-DD</Date>
                                                                <BrokerCommission>Broker_Commission</BrokerCommission>
                                                              </PortfolioElementModel>
                                                            </PortfolioModel

21) Type the name of file to be uploaded and press enter.Type the complete file name including extension.
    Here is a sample for uploading a portfolio with 2 stocks:
        For Inflexible Portfolio:
                                                            <PortfolioModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>GOOG</StockTicker>
                                                                <totalQuantity>100</totalQuantity>
                                                                <Date>2022-09-22</Date>
                                                              </PortfolioElementModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>MSFT</StockTicker>
                                                                <totalQuantity>10</totalQuantity>
                                                                <Date>2022-09-15</Date>
                                                              </PortfolioElementModel>
                                                            </PortfolioModel>
        For Flexible Portfolio:
                                                            <PortfolioModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>GOOG</StockTicker>
                                                                <totalQuantity>100</totalQuantity>
                                                                <Date>2022-09-22</Date>
                                                                <BrokerCommission> 50.9</BrokerCommission>
                                                              </PortfolioElementModel>
                                                              <PortfolioElementModel>
                                                                <StockTicker>MSFT</StockTicker>
                                                                <totalQuantity>10</totalQuantity>
                                                                <Date>2022-09-15</Date>
                                                                <BrokerCommission>70.0</BrokerCommission>
                                                              </PortfolioElementModel>
                                                            </PortfolioModel>

22) You will see a success message of portfolio being added. Repeat step 16 to view portfolio.

23) To determine the value of portfolio on a date, Input 6 and press enter. Input the portfolio number
    whose value you want to calculate and press enter.

24) Enter the date in the displayed format.
    Please ensure you enter a date which does not fall when market is closed, i.e. weekend or public holiday.
    The system will give a feedback if there is no data for the given date for the stock due to holiday or not being traded.

25) It will display the combined value of all the stocks in the chosen portfolio for chosen date.

26) To do this for another date, repeat from step 23 again with a different date this time.

27) To determine cost basis for a given portfolio, Press 4 and hit enter.

28) Select the appropriate portfolio from the options. We support cost basis calculation for both flexible and inflexible portfolios.

29) Enter the date for which you want to view the cost basis. This means all the transactions upto that date will be included for the calculation.
    We support cost basis calculation for both flexible and inflexible portfolios.

30) If you want to view the cost basis for another date, please repeat from steps 27 again.

31) You can also choose to download a portfolio to your workstation. Input 4 and press enter.
    Input the portfolio number you want to download and press enter.

32) You can also choose to view the performance of your portfolio, over a particular period of time. Please be patient as it may take few seconds, to compute
    the performance.

33) The complete portfolio will be downloaded to a downloads folder created in the same directory as the jar file.
    The downloaded portfolio folder has been further subdivided into various folders and xml files for more readability.
    Every folder has an xml file which contains immediate details of the rest of the subfolders.

34) If you shutdown the jar file, please ensure the jar file is again run from the same directory where UserSetModel folder is.
    Our program at various stages during running, saves all the data created into this folder and on restarting attempts to read data from this XML.
    A portfolio will only be saved if all the data for it has been entered before the system was shut down.

35) There is also a provision of logging out or exiting the system. Another user can then log in by entering their userId.




