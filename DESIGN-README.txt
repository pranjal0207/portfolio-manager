Design Changes:
1. Created new Graphical User Interface i.e. created new GUIView interface.
2. Created new HomeView class implementing GUIView interface.
3. Created new Controller called GUIController implementing the Controller interface.
4. Created an instance of new Controller and view in Runner which initializes on passing "gui" as command line interface to the program.
5. Added an enum for Strategy.
6. Created new Strategy interface.
7. Created Dollar Cost Averaging Strategy implementing Strategy interface.
8. Created new Strategical Flexible Portfolio Model extending Flexible Portfolio Model to support new functionalities.
9. Abstracted Portfolio Builder to avoid code repetition and boost code reuse for building all types of portfolios.
10. Added 'add strategy' and 'run all strategy' methods in portfolio interface.
11. Added 'get price on multiple dates' functionality in Stock Model interface and Stock Model implementation.
12. Added 'run all strategy' method in user model.
13. Added 'source' in transaction model to denote whether a strategy created a transaction or it was added manually by the user.
14. Added write strategy functionality in data persister interface and xml writer class.
15. Added read strategy functionality in xml parser.

Design:
We designed our system with an MVC (Model View Controller) model.
In our model, User Stock Model is the top model consisting of a User Set Model and a Stock Set Model.
User Set Model consists of a list of UserModels where a UserModel is a unique user.
A User Model consists of a unique userId, username and a map of portfolio models. User Id's start from 1000 and are
auto incremented for every new user.
The portfolio name uniquely identifies a Portfolio Model for a user. A portfolio model also consists of the timestamp when
the portfolio was created, and a map of various stock symbols and their corresponding portfolio element models.
A portfolio model can be of two types -- Flexible and Inflexible Portfolio Model
A Flexible portfolio model can now be of another type called Strategical Flexible model which allows adding strategies.
Currently, we have Dollar Cost Averaging Strategy implementation, but the design supports adding more strategies in the future.
A Portfolio Element Model consists of the average price of the stock in the given portfolio, the total quantity of stock in the portfolio, the latest transaction date for that stock, a list of transactions on that particular portfolio element model and a Stock Model Object.
A Stock Model stores all the attributes of a stock. It holds the stock name, stock ticker symbol, the stock exchange where the stock is traded
and the IPO date of the stock.
Having a portfolio element model gives us the flexibility to add more features in future to one kind of stock in a portfolio, for instance, keeping a buy/sell history of a stock in a portfolio.

To persist the data even after the program stops running, we are storing all the data in various XML files and folders.
Each model has its XML file, and it contains additional XML files in multiple subfolders containing details of the class's various objects. Whenever an operation is completed, like creating a user or adding a portfolio, new XML files are generated and stored
in the UserSetModel folder. When the program runs again, it checks in the specified UserSetModel folder whether any data exists and reads the same
from XML files.

The XML parser is written in a generic form. It works with all kinds of XML, which gives the flexibility to provide users with features like uploading their portfolio via XML or downloading the portfolio to the system. Users can place an XML file in the same directory as the program and give its name
to the program to create a portfolio. The download functionality creates a new download folder in the same directory and writes the portfolio required and its various
objects as XML files. We support two formats for XML, on format is to increase reader usability to view their downloaded portfolio, where we subdivide each portfolio element and stock
further and have a separate XML for each. In upload functionality, we allow user to upload one single file which contains the relevant information about the different stocks they are entering.

Our design supports two different views - Text based view and Graphical User Interface view.

The Graphical User Interface currently uses Java Swing to build its views but is open to supporting other types of views and action listeners.
Home View is the current GUI view implementation which implements the GUI View interface.
If we need to write a new GUI, we would require implementing GUI View.

Text Based View have been written as text based displays which display the text being sent by the controller.
The View currently is text-based only and provides functionality to print various messages to the user.
If required, the view can be changed to some other GUI based view by writing a new view and providing the required methods to the controller.

Our design supports two different controllers - One for Text Based View and other for Graphical User interface.

The controller controls the flow of the system. It takes all the inputs and performs appropriate validations on the same.
The controller mainly interacts with the User Stock model. This model in turn call other models (Stock Set model and the User set model) and provide the necessary result to the view.
Controller can decide when to call the model and when to give the data from the model to the view.
The controller is coupled with both the view and the model and acts as a bridge between them.

The system supports all stocks currently supported by the AlphaVantageAPI and has no restriction on the dates supported. This means, if the data for a particular stock is available from the AlphaVantageAPI, we are supporting all those dates.

Whenever the program initializes, it hits the AlphaVantageAPI to fetch a list of supported stocks if not already present. After this, whenever this data is required, it is picked up from the CSV file written and not from the API thus limiting the number of API calls made.
Similarly, to save repeated API calls, we are storing the data of any stock used on a need-basis. If the date for which the data is required is not found in the CSV, only then it will try to fetch the data again from the API thus "caching" the data and only refreshing the cached data when required.
This makes the program more responsive.

The program takes in a command line argument ('gui') to decide which view and controller to spin off.

Structure of the project :-
Main runner class calls the controller which in turn delegates operations to both view and model.
UserSetModel folder acts as a database store for storing all the data in XML files.
StockSetModel folder acts as a database store for storing all the data from the API into CSV files.
Downloads folder is created when user wants to download a portfolio to system.
TestingHelper folder contains various dummy data for running tests on our code.
