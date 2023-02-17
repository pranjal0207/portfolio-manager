package models;

/**
 * Enum class to store various supported strategy names.
 */
public enum Strategy {
  DOLLAR_COST_AVERAGING("Dollar Cost Averaging");

  private final String name;

  Strategy(final String name) {
    this.name = name;
  }

  /**
   * Get the strategy string value.
   * @param name name of strategy.
   * @return equivalent string value name.
   */
  public static Strategy fromString(String name) {
    for (Strategy strategy : Strategy.values()) {
      if (strategy.name.equalsIgnoreCase(name)) {
        return strategy;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return name;
  }
}
