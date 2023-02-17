package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Util class to get the date range for gicn dates.
 */
public class DateRangeCalculatorUtil {
  /**
   * Get the date range for start and end date.
   *
   * @param startDate start date to start date range.
   * @param endDate   end date to end date range.
   * @return DateRange object with start and end date.
   */
  public static DateRange getDateRange(Date startDate, Date endDate) {
    LocalDate startDateLocal = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate endDateLocal = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    long totalYears = getTotalYears(startDateLocal, endDateLocal);
    if (totalYears >= 5 && totalYears <= 30) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.lastDayOfYear(), 1, totalYears);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.YEARLY);
    } else if (totalYears > 120) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.lastDayOfYear(), 4, totalYears / 4);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.YEARLY);
    } else if (totalYears > 60) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.lastDayOfYear(), 3, totalYears / 3);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.YEARLY);
    } else if (totalYears > 30) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.lastDayOfYear(), 2, totalYears / 2);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.YEARLY);
    }

    long totalMonths = getTotalMonths(startDateLocal, endDateLocal);
    if (totalMonths >= 5 && totalMonths <= 30) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.lastDayOfMonth(), 1, totalMonths);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.MONTHLY);
    } else if (totalMonths > 30) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.lastDayOfMonth(), 2, totalMonths / 2);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.MONTHLY);
    }

    long totalWeeks = getTotalWeeks(startDateLocal, endDateLocal);
    if (totalWeeks >= 5 && totalWeeks <= 30) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.next(DayOfWeek.FRIDAY), 1, totalWeeks);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.DAILY);
    } else if (totalWeeks > 30) {
      List<LocalDate> dates = getDatesRangesGivenStartDate(startDateLocal,
              TemporalAdjusters.next(DayOfWeek.FRIDAY), 2, totalWeeks / 2);
      if (dates.get(dates.size() - 1).compareTo(endDateLocal) < 0) {
        dates.add(endDateLocal);
      }
      return new DateRange(filterNonWorkingDays(dates), DateRangeType.DAILY);
    }

    long totalDays = getTotalDays(startDateLocal, endDateLocal);
    List<LocalDate> dates;
    if (totalDays <= 30) {
      dates = getDatesRangesGivenStartDate(startDateLocal,
              null, 1, totalDays);
    } else {
      dates = getDatesRangesGivenStartDate(startDateLocal,
              null, 2, totalDays / 2);
    }
    return new DateRange(filterNonWorkingDays(dates), DateRangeType.DAILY);
  }

  private static List<Date> filterNonWorkingDays(List<LocalDate> dates) {
    List<Date> filteredDates = new ArrayList<>();
    for (LocalDate date : dates) {
      if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
              || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
        date = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
      }
      filteredDates.add(Date.from(date.atStartOfDay(ZoneId.of("America/New_York")).toInstant()));
    }
    return filteredDates;
  }

  private static long getTotalDays(LocalDate startDateLocal, LocalDate endDateLocal) {
    if (startDateLocal.compareTo(endDateLocal) == 0) {
      return 1;
    }

    long totalDays = 0;
    while (!startDateLocal.equals(endDateLocal)) {
      startDateLocal = startDateLocal.plusDays(1);
      totalDays++;
    }
    return totalDays + 1;
  }

  private static long getTotalMonths(LocalDate startDateLocal, LocalDate endDateLocal) {
    LocalDate lastDateOfStartMonthLocal = startDateLocal
            .with(TemporalAdjusters.lastDayOfMonth());
    LocalDate lastDateOfEndMonthLocal = endDateLocal
            .with(TemporalAdjusters.lastDayOfMonth());
    return ChronoUnit.MONTHS.between(lastDateOfStartMonthLocal, lastDateOfEndMonthLocal);
  }

  private static long getTotalWeeks(LocalDate startDateLocal, LocalDate endDateLocal) {
    LocalDate lastDateOfStartMonthLocal = startDateLocal
            .with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    LocalDate lastDateOfEndMonthLocal = endDateLocal
            .with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    return ChronoUnit.WEEKS.between(lastDateOfStartMonthLocal, lastDateOfEndMonthLocal);
  }

  private static long getTotalYears(LocalDate startDateLocal, LocalDate endDateLocal) {
    LocalDate lastDateOfStartYearLocal = startDateLocal.with(TemporalAdjusters.lastDayOfYear());
    LocalDate lastDateOfPreviousToEndYearLocal = endDateLocal
            .with(TemporalAdjusters.lastDayOfYear());
    return ChronoUnit.YEARS.between(lastDateOfStartYearLocal, lastDateOfPreviousToEndYearLocal);
  }

  private static List<LocalDate> getDatesRangesGivenStartDate(LocalDate date,
                                                              TemporalAdjuster temporalAdjuster,
                                                              int skipTimes,
                                                              long repetitions) {
    List<LocalDate> dates = new ArrayList<>();
    int skip = skipTimes;
    for (long i = 0; i < repetitions; i++) {
      while (skip > 0) {
        if (temporalAdjuster != null) {
          date = date.with(temporalAdjuster);
        }
        if (skip == 1) {
          dates.add(date);
        }
        date = date.plusDays(1);
        skip--;
      }
      skip = skipTimes;
    }

    return dates;
  }

  /**
   * Static method to convert the date one format to other.
   * @param date date as string.
   * @param dateFormatFrom format from date to be changed.
   * @param dateFormatTo format to which date needs to be changed.
   * @return the new date in string format only.
   */
  public static String dateConverter(String date, String dateFormatFrom, String dateFormatTo) {
    DateFormat inputFormat = new SimpleDateFormat(dateFormatFrom);
    DateFormat outputFormat = new SimpleDateFormat(dateFormatTo);
    try {
      Date newDate = inputFormat.parse(date);
      return outputFormat.format(newDate);
    } catch (ParseException e) {
      return "";
    }
  }
}
