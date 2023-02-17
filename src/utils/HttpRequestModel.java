package utils;

/**
 * Interface containing various methods related to HTTP calls.
 */
public interface HttpRequestModel {
  /**
   * Method to fetch data from a given URL.
   *
   * @param url url link from which data needs to be fetched.
   * @return the fetched data as a string only.
   */
  String fetchUrl(String url);
}
