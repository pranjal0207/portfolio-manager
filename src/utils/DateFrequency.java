package utils;

/**
 * Enum to define different date ranges possible.
 */
public enum DateFrequency {
  WEEKLY(1),
  MONTHLY(2),
  YEARLY(3);

  private final int legIndex;

  /**
   * Constructor to set the index of range.
   *
   * @param legIndex index to be set to a particular range.
   */
  DateFrequency(int legIndex) {
    this.legIndex = legIndex;
  }

  /**
   * Method to getFrequency from index.
   *
   * @param legIndex index from which frequency needs to be calculated.
   * @return DateFrequency object with frequency.
   */
  public static DateFrequency getFrequency(int legIndex) {
    for (DateFrequency d : DateFrequency.values()) {
      if (d.legIndex == legIndex) {
        return d;
      }
    }
    throw new IllegalArgumentException("Frequency not found");
  }
}
