import java.sql.SQLException;

import controller.Controller;
import controller.GUIController;
import models.Model;
import views.GUIView;
import views.HomeView;

/**
 * Runner class which starts the portfolio system.
 */
public class Runner {
  /**
   * Main class to start the program. It initialises
   * userSetModel, stockSetModel, textBasedView,
   * controller and calls controller to display options.
   *
   * @param args arguments required for java to run main.
   */
  public static void main(String[] args) throws SQLException {
    GUIView guiView = new HomeView();

    if(args.length != 2) {
      System.out.println("Please provide username and password of your database as command line argument");
      System.exit(0);
    }
    Model model = new Model(args[0], args[1]);
    Controller controller = new GUIController(model, guiView);
  }
}
