package views;

import java.util.List;

import javax.swing.JTable;

/**
 * Panel class to show composition panel.
 * This panel extends the jpanel and all its features.
 */
public class CompositionPanelCreator {
  private static Object[][] getData(List<List<String>> composition) {
    if (composition.size() == 0) {
      return new Object[][]{{"Ticker Symbol", "Stock Name", "Total Quantity",
              "Average Price", "Latest Transaction Date"}};
    }


    String[][] data = new String[composition.size() + 1][composition.get(0).size()];
    data[0][0] = "Ticker Symbol";
    data[0][1] = "Stock Name";
    data[0][2] = "Total Quantity";
    data[0][3] = "Average Price";
    data[0][4] = "Last Transaction Date";

    for (int i = 1; i <= composition.size(); i++) {
      data[i][0] = composition.get(i - 1).get(0);
      data[i][1] = composition.get(i - 1).get(1);
      data[i][2] = composition.get(i - 1).get(2);
      data[i][3] = composition.get(i - 1).get(3);
      data[i][4] = composition.get(i - 1).get(4);
    }
    return data;
  }

  /**
   * Static method to get the composition panel table.
   * @param composition list of compositions.
   * @return return a Jtable object only.
   */
  public static JTable getCompositionPanelTable(List<List<String>> composition) {
    JTable table = new JTable(getData(composition), getColumns());
    table.setEnabled(false);
    table.setFillsViewportHeight(true);
    return table;
  }

  private static Object[] getColumns() {
    return new String[]{"Ticker Symbol", "Stock Name",
        "Total Quantity", "Average Price", "Latest Transaction Date"};
  }

}
