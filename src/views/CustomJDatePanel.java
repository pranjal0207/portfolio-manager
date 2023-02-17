package views;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Date;
import java.util.Properties;

/**
 * Class to make a custom datePanel.
 */
public class CustomJDatePanel {
  /**
   * Method to get back the custom date panel.
   * @return JDatePanel implementation object only.
   */
  public static JDatePanelImpl getCustomJDatePanel() {
    DateModel<Date> dateModel = new UtilDateModel();
    Properties properties = new Properties();
    properties.put("text.today", "Today");
    properties.put("text.month", "Month");
    properties.put("text.year", "Year");
    return new JDatePanelImpl(dateModel, properties);
  }
}
