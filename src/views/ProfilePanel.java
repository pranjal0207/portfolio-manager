package views;

import java.awt.*;

import javax.swing.*;

import controller.Features;

public class ProfilePanel extends JPanel {
  private JLabel lNickName;
  private JLabel lPhoneNumber;
  private JLabel lPassword;

  private JTextField tNickName;
  private JTextField tPhoneNumber;
  private JTextField tPassword;

  private JButton bSave;
  private JButton bBack;

  public ProfilePanel() {
    initializeViews();
  }

  protected void setProfile(String nickName, String phoneNumber, String password) {
    tNickName.setText(nickName);
    tPhoneNumber.setText(phoneNumber);
    tPassword.setText(password);
  }

  private void initializeViews() {
    lNickName = new JLabel("Nick Name", JLabel.CENTER);
    lNickName.setAlignmentX(Component.CENTER_ALIGNMENT);

    lPhoneNumber = new JLabel("Phone Number", JLabel.CENTER);
    lPhoneNumber.setAlignmentX(Component.CENTER_ALIGNMENT);

    lPassword = new JLabel("Password", JLabel.CENTER);
    lPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

    tNickName = new JTextField(20);
    tPhoneNumber = new JTextField(20);
    tPassword = new JTextField(20);

    bSave = new JButton("Save");
    bBack = new JButton("Back");

    lPhoneNumber.setMaximumSize(new Dimension(150, 20));
    lPhoneNumber.setMinimumSize(new Dimension(150, 20));
    lPhoneNumber.setPreferredSize(new Dimension(150, 20));

    lNickName.setMaximumSize(new Dimension(150, 20));
    lNickName.setMinimumSize(new Dimension(150, 20));
    lNickName.setPreferredSize(new Dimension(150, 20));

    lPassword.setMaximumSize(new Dimension(150, 20));
    lPassword.setMinimumSize(new Dimension(150, 20));
    lPassword.setPreferredSize(new Dimension(150, 20));

    JPanel firstPanel = new JPanel();
    firstPanel.add(lNickName);
    firstPanel.add(tNickName);

    JPanel secondPanel = new JPanel();
    secondPanel.add(lPhoneNumber);
    secondPanel.add(tPhoneNumber);

    JPanel thirdPanel = new JPanel();
    thirdPanel.add(lPassword);
    thirdPanel.add(tPassword);

    JPanel fourthPanel = new JPanel();
    fourthPanel.add(bSave);
    fourthPanel.add(bBack);

    this.setLayout(new GridLayout(0, 1));
    this.add(firstPanel);
    this.add(secondPanel);
    this.add(thirdPanel);
    this.add(fourthPanel);
  }

  protected void addFeatures(Features features) {
    bBack.addActionListener((e) -> features.goToMainMenu());
    bSave.addActionListener((e) -> features.updateProfile(tNickName.getText(), tPhoneNumber.getText(), tPassword.getText()));
  }
}
