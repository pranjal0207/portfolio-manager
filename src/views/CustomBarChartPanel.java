package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import utils.ChartPlot;

/**
 * Panel class to create custom bar chart panel.
 * This panel extends the jpanel and all its features.
 */
public class CustomBarChartPanel extends JPanel {
  private final int barHeight;
  private final int barWidth;
  private final int barGap;
  private final Color color;
  private final int shadow;

  private JPanel barPanel;
  private JPanel labelPanel;
  private JPanel scalePanel;
  private ChartPlot chartPlot;

  /**
   * Public constructor to initialise a custom bar chart chanel.
   *
   * @param color     color of bar chart.
   * @param barHeight height of bar.
   * @param barWidth  width of bar.
   * @param barGap    gap between bars.
   * @param shadow    shadow of bar.
   */
  public CustomBarChartPanel(Color color, int barHeight, int barWidth, int barGap, int shadow) {
    this.color = color;
    this.barGap = barGap;
    this.barWidth = barWidth;
    this.shadow = shadow;
    this.barHeight = barHeight;
    initializeViews();
  }

  private void initializeViews() {
    setBorder(new EmptyBorder(10, 10, 10, 10));
    setLayout(new BorderLayout());

    barPanel = new JPanel(new GridLayout(1, 0, barGap, 0));
    Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
    Border inner = new EmptyBorder(10, 10, 0, 10);
    Border compound = new CompoundBorder(outer, inner);
    barPanel.setBorder(compound);

    labelPanel = new JPanel(new GridLayout(1, 0, barGap, 0));
    labelPanel.setBorder(new EmptyBorder(5, 10, 0, 10));

    scalePanel = new JPanel();

    add(barPanel, BorderLayout.CENTER);
    add(labelPanel, BorderLayout.PAGE_END);
    add(scalePanel, BorderLayout.PAGE_START);
  }

  protected void setData(ChartPlot chartPlot) {
    this.chartPlot = chartPlot;
    buildGraph();
    repaint();
  }

  /**
   * Method to build the graph.
   */
  public void buildGraph() {
    barPanel.removeAll();
    labelPanel.removeAll();
    scalePanel.removeAll();

    int maxValue = 0;
    int minValue = Integer.MAX_VALUE;

    for (Integer value : chartPlot.getPlots().values()) {
      maxValue = Math.max(maxValue, value);
      if (value != 0) {
        minValue = Math.min(minValue, value);
      }
    }

    if (minValue == Integer.MAX_VALUE) {
      minValue = 0;
    }

    int minPixelHeight = maxValue == 0 ? 0 : minValue * barHeight / maxValue;

    for (Map.Entry<String, Integer> entry : chartPlot.getPlots().entrySet()) {
      JLabel currentBar = new JLabel(entry.getValue() * minPixelHeight + "px");
      currentBar.setHorizontalTextPosition(JLabel.CENTER);
      currentBar.setHorizontalAlignment(JLabel.CENTER);
      currentBar.setVerticalTextPosition(JLabel.TOP);
      currentBar.setVerticalAlignment(JLabel.BOTTOM);
      int currentBarHeight = maxValue == 0 ? 0 : (entry.getValue() * barHeight) / maxValue;
      Icon icon = new BarIcon(this.color, barWidth, currentBarHeight, this.shadow);
      currentBar.setIcon(icon);
      barPanel.add(currentBar);
      JLabel barLabel = new JLabel(entry.getKey());
      barLabel.setFont(new Font(barLabel.getFont().getName(), Font.PLAIN, 10));
      barLabel.setHorizontalAlignment(JLabel.CENTER);
      labelPanel.add(barLabel);
    }

    JPanel title = new JPanel();
    title.setLayout(new GridLayout(0, 1));
    JLabel performanceLabel = new JLabel("Performance", SwingConstants.CENTER);
    performanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    performanceLabel.setFont(new Font(performanceLabel.getFont().getName(),
            Font.BOLD, performanceLabel.getFont().getSize()));
    title.add(performanceLabel);
    JLabel lScale = new JLabel(String.format(
            "Relative scale: First %d pixels = USD%d and following %d pixels = USD%f",
            minPixelHeight, chartPlot.getStart(), minPixelHeight, chartPlot.getScale()));
    title.add(lScale);
    scalePanel.add(title, BorderLayout.PAGE_START);
  }

  private static class BarIcon implements Icon {
    private final int shadow;
    private final Color color;
    private final int width;
    private final int height;

    public BarIcon(Color color, int width, int height, int shadow) {
      this.color = color;
      this.width = width;
      this.height = height;
      this.shadow = shadow;
    }

    public int getIconWidth() {
      return width;
    }

    public int getIconHeight() {
      return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
      g.setColor(color);
      g.fillRect(x, y, width - shadow, height);
      g.setColor(Color.GRAY);
      g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
    }
  }
}