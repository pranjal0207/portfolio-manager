package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Util class which keeps tracks of all the
 * final constraints of the chart.
 */
public class ChartPlotCalculatorUtil {

  /**
   * Method to build a chart plot as per the constraints.
   *
   * @param datePriceMap  map of all the date prices.
   * @param dateRangeType date range object with the range
   * @param maxEntries    number of max entries allowed
   * @return chart plot object with using the pri
   */
  public static ChartPlot buildChartPlot(Map<Date, Double> datePriceMap,
                                         DateRangeType dateRangeType,
                                         int maxEntries) {
    Map<String, Integer> plots = new LinkedHashMap<>();
    double lowest = Double.MAX_VALUE;
    double highest = Double.MIN_VALUE;

    for (Map.Entry<Date, Double> entry : datePriceMap.entrySet()) {
      if (entry.getValue() < lowest && entry.getValue() != 0) {
        lowest = entry.getValue();
      }
      if (entry.getValue() > highest && entry.getValue() != 0) {
        highest = entry.getValue();
      }
    }

    int lowestInt = (int) lowest;
    int highestInt = (int) highest;

    double steps = (double) (highestInt - lowestInt) / (maxEntries - 2);

    for (Map.Entry<Date, Double> entry : datePriceMap.entrySet()) {
      if (entry.getValue() != 0 && steps >= 1) {
        plots.put(getDateLabel(entry.getKey(), dateRangeType),
                1 + (int) ((entry.getValue() - lowestInt) / steps));
      } else if (entry.getValue() != 0) {
        steps = 1;
        plots.put(getDateLabel(entry.getKey(), dateRangeType),
                1 + (int) ((entry.getValue() - lowestInt)));
      } else {
        plots.put(getDateLabel(entry.getKey(), dateRangeType), 0);
      }
    }

    if (lowest == Double.MAX_VALUE) {
      lowestInt = 0;
    }
    if (highest == Double.MIN_VALUE) {
      steps = 0;
    }

    return new ChartPlot(plots, lowestInt, steps, dateRangeType);
  }

  /**
   * Get the date label for a date as per the given date range.
   *
   * @param date          date which needs to be formatted.
   * @param dateRangeType given date range as per the format required.
   * @return the date label as a string only.
   */
  public static String getDateLabel(Date date, DateRangeType dateRangeType) {
    DateFormat monthFormat = new SimpleDateFormat("MMM yyyy");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    if (dateRangeType == DateRangeType.MONTHLY || dateRangeType == DateRangeType.YEARLY) {
      return monthFormat.format(date);
    }

    return dateFormat.format(date);
  }
}