package views;



import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controller.Features;

/**
 * Panel class to show the login panel. It extends JPanel ad all its features.
 */
public class LoginPanel extends JPanel {
  private JTextField tUserNameLogin;
  private JTextField tUserNameSignUp;
  private JTextField tPasswordLogin;
  private JTextField tPasswordSignUp;

  private JButton bLogin;
  private JButton bSignIn;
  private JButton bExit;
  private JButton bSelectBrokers;

  private List<String> brokers;
  private List<String> selectedBrokers;
  private Features features;
  private JFrame parent;
  private JDialog selectBrokerDialog;

  /**
   * Public constructor to initialize login panel.
   */
  public LoginPanel(JFrame parent) {
    this.parent = parent;
    initializeView();
  }

  private void initializeView() {
    this.selectedBrokers = new ArrayList<>();
    BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
    this.setLayout(boxLayout);

    this.add(Box.createVerticalGlue());
    JLabel lLoginPrompt1 = new JLabel("Already a user?", JLabel.CENTER);
    lLoginPrompt1.setAlignmentX(CENTER_ALIGNMENT);
    this.add(lLoginPrompt1);
    JLabel lLoginPrompt2 = new JLabel("Login with user name and password", JLabel.CENTER);
    lLoginPrompt2.setAlignmentX(CENTER_ALIGNMENT);
    this.add(lLoginPrompt2);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    tUserNameLogin = new JTextField(10);
    tUserNameLogin.setMaximumSize(tUserNameLogin.getPreferredSize());
    this.add(tUserNameLogin);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    tPasswordLogin = new JTextField(10);
    tPasswordLogin.setMaximumSize(tPasswordLogin.getPreferredSize());
    this.add(tPasswordLogin);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    bLogin = new JButton("Login");
    bLogin.setAlignmentX(CENTER_ALIGNMENT);
    this.add(bLogin);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    JLabel lSignInPrompt1 = new JLabel("Create a new account?", JLabel.CENTER);
    lSignInPrompt1.setAlignmentX(CENTER_ALIGNMENT);
    this.add(lSignInPrompt1);

    JLabel lSignInPrompt2 = new JLabel("Enter your username", JLabel.CENTER);
    lSignInPrompt2.setAlignmentX(CENTER_ALIGNMENT);
    this.add(lSignInPrompt2);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    tUserNameSignUp = new JTextField(10);
    tUserNameSignUp.setMaximumSize(tUserNameSignUp.getPreferredSize());
    tUserNameSignUp.setAlignmentX(CENTER_ALIGNMENT);
    this.add(tUserNameSignUp);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    tPasswordSignUp = new JTextField(10);
    tPasswordSignUp.setMaximumSize(tPasswordSignUp.getPreferredSize());
    tPasswordSignUp.setAlignmentX(CENTER_ALIGNMENT);
    this.add(tPasswordSignUp);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    bSelectBrokers = new JButton("Select Brokers");
    bSelectBrokers.setAlignmentX(CENTER_ALIGNMENT);
    this.add(bSelectBrokers);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    bSignIn = new JButton("Create Account");
    bSignIn.setAlignmentX(CENTER_ALIGNMENT);
    this.add(bSignIn);

    this.add(Box.createRigidArea(new Dimension(0, 30)));

    bExit = new JButton("Exit");
    bExit.setAlignmentX(CENTER_ALIGNMENT);
    this.add(bExit);

    this.add(Box.createVerticalGlue());
  }

  protected void addFeatures(Features features) {
    this.features = features;
    bLogin.addActionListener((e) -> features.login(tUserNameLogin.getText(), tPasswordLogin.getText()));
    bSignIn.addActionListener((e) -> signUp());
    bExit.addActionListener((e) -> features.exitProgram());
    bSelectBrokers.addActionListener((e) -> features.requestBrokersSelection());
  }

  private void signUp() {
    if(selectedBrokers.size() == 0) {
      JOptionPane.showMessageDialog(null,
              "Select at least one brokerage firm to link account with");
      return;
    }
    features.signUp(tUserNameSignUp.getText(), tPasswordSignUp.getText(), selectedBrokers);
  }

  protected String getUserId() {
    return tUserNameLogin.getText();
  }

  protected String getUserName() {
    return tUserNameSignUp.getText();
  }

  protected void clearInputs() {
    this.tUserNameSignUp.setText("");
    this.tUserNameLogin.setText("");
    this.tPasswordLogin.setText("");
    this.tPasswordSignUp.setText("");
  }

  protected void setBrokers(List<String> brokers) {
    this.brokers = brokers;
  }

  protected void setSelectedBrokers(List<String> brokers) {
    this.selectedBrokers = brokers;
    selectBrokerDialog.setVisible(false);
  }

  protected void requestBrokersSelection() {
    SelectBrokerPanel selectBrokerPanel = new SelectBrokerPanel(brokers);
    selectBrokerPanel.addFeatures(this.features);
    selectBrokerDialog = new JDialog(parent,
            "Select Brokers", true);
    selectBrokerDialog.setLocation(300, 200);
    selectBrokerDialog.setSize(500, 500);
    selectBrokerDialog.setContentPane(selectBrokerPanel);
    selectBrokerDialog.pack();
    selectBrokerDialog.setResizable(false);
    selectBrokerDialog.setVisible(true);
  }
}
