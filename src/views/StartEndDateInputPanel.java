package views;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *  Panel class to show the start and end date input.
 *  It extends JPanel ad all its features.
 */
public class StartEndDateInputPanel extends JPanel {
  private DateInputPanel diStartDate;
  private DateInputPanel diEndDate;

  /**
   * Public constructor to initialize start and end date input panel.
   */
  public StartEndDateInputPanel() {
    initializeViews();
  }

  private void initializeViews() {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    JLabel lStartDate = new JLabel("Start Date", SwingConstants.CENTER);
    lStartDate.setAlignmentX(Component.CENTER_ALIGNMENT);

    diStartDate = new DateInputPanel();
    diStartDate.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel lEndDate = new JLabel("End Date", SwingConstants.CENTER);
    lEndDate.setAlignmentX(Component.CENTER_ALIGNMENT);

    diEndDate = new DateInputPanel();
    diEndDate.setAlignmentX(Component.CENTER_ALIGNMENT);

    this.add(lStartDate);
    this.add(diStartDate);
    this.add(lEndDate);
    this.add(diEndDate);
  }

  protected String getStartDate() {
    return diStartDate.getDate();
  }

  protected String getEndDate() {
    return diEndDate.getDate();
  }
}
