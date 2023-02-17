package views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controller.Features;

import static javax.swing.BoxLayout.PAGE_AXIS;

public class SelectBrokerPanel extends JPanel {
  private JButton bSave;
  private List<String> brokers;
  private JCheckBox[] bBrokers;

  private JLabel lBrokers;

  public SelectBrokerPanel(List<String> brokers) {
    this.brokers = brokers;
    initializeViews();
  }

  private void initializeViews() {
    lBrokers = new JLabel("Select brokers you want your account with", JLabel.CENTER);
    lBrokers.setAlignmentX(Component.CENTER_ALIGNMENT);

    bBrokers = new JCheckBox[brokers.size()];
    for(int i = 0; i < brokers.size(); i++) {
      bBrokers[i] = new JCheckBox(brokers.get(i));
      bBrokers[i].setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    bSave = new JButton("Save");
    bSave.setAlignmentX(Component.CENTER_ALIGNMENT);

    this.setLayout(new BoxLayout(this, PAGE_AXIS));

    this.add(lBrokers);
    for(JCheckBox brokerBox: bBrokers)
      this.add(brokerBox);
    this.add(bSave);
  }

  protected void addFeatures(Features features) {
    bSave.addActionListener((e) -> features.closeChooseBrokerPanel(getSelectedItems()));
  }

  private List<String> getSelectedItems() {
    List<String> items = new ArrayList<>();
    for(JCheckBox box : bBrokers)
      if(box.isSelected())
        items.add(box.getText());

    return items;
  }
}
