package utils;

import java.util.Date;
import java.util.List;

/**
 * Class which stores the date range.
 * It has the list of all the dates and type of date range.
 */
public class DateRange {
  private final List<Date> dateList;
  private final DateRangeType dateRangeType;

  /**
   * Constructor to initialise a date range object.
   *
   * @param dateList      list of dates.
   * @param dateRangeType date range type enum
   */
  public DateRange(List<Date> dateList, DateRangeType dateRangeType) {
    this.dateList = dateList;
    this.dateRangeType = dateRangeType;
  }

  /**
   * Method to get the datelist.
   *
   * @return date list as a list of dates only.
   */
  public List<Date> getDateList() {
    return dateList;
  }

  /**
   * Method to get the date range type.
   *
   * @return date range type enum.
   */
  public DateRangeType getDateRangeType() {
    return dateRangeType;
  }
}
