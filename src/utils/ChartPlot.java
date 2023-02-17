package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to plot the chart. It stores various immutable parameters
 * and scale to design a chart.
 */
public class ChartPlot {
  private final Map<String, Integer> plots;
  private final double scale;
  private final int start;

  private final DateRangeType dateRangeType;

  /**
   * Public constructor to initialize a chart plot.
   *
   * @param plots         map of all the date plots
   * @param start         start date of chart
   * @param scale         scale for asterisks of chart
   * @param dateRangeType range of chart given
   */
  public ChartPlot(Map<String, Integer> plots,
                   int start, double scale, DateRangeType dateRangeType) {
    this.plots = plots;
    this.scale = scale;
    this.start = start;
    this.dateRangeType = dateRangeType;
  }

  /**
   * Public constructor to initialize a chart plot.
   */
  public ChartPlot() {
    this.plots = new HashMap<>();
    this.scale = 0;
    this.start = 0;
    this.dateRangeType = DateRangeType.DAILY;
  }

  /**
   * Method to get all the plots of a chart.
   *
   * @return a map of date and its plot as a map only.
   */
  public Map<String, Integer> getPlots() {
    return plots;
  }

  /**
   * Method to get the scale of the chart.
   *
   * @return the chart's scale as double only.
   */
  public double getScale() {
    return scale;
  }

  /**
   * Get the start value of chart.
   *
   * @return the start value of chart as integer only.
   */
  public int getStart() {
    return start;
  }

  /**
   * Get the date range of the chart.
   *
   * @return the date range of chart as an enum only.
   */
  public DateRangeType getDateRangeType() {
    return dateRangeType;
  }

}
