package views;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.DateRangeCalculatorUtil;

/**
 * Panel class to create Date Input panel.
 * This panel extends the jpanel and all its features.
 */
public class DateInputPanel extends JPanel {
  private JDatePickerImpl dpDate;

  /**
   * Constructor to create date input panel.
   */
  public DateInputPanel() {
    initializeView();
  }

  private void initializeView() {
    JLabel lInputDate = new JLabel("Input Date");
    dpDate = new JDatePickerImpl(CustomJDatePanel.getCustomJDatePanel(),
            new DateComponentFormatter());

    this.setLayout(new GridLayout(0, 1));
    this.add(lInputDate);
    this.add(dpDate);
  }

  protected String getDate() {
    if (this.dpDate.getModel().getValue() != null) {
      String date = this.dpDate.getModel().getValue().toString();
      return DateRangeCalculatorUtil.dateConverter(date,
              "EEE MMM dd HH:mm:ss zzzz yyyy", "yyyy-MM-dd");
    } else {
      return "";
    }
  }
}
