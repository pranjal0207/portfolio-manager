package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class implements all the methods of HttpRequestModel.
 * It contains method implementations for doing http requests.
 */
public class HttpRequestModelImpl implements HttpRequestModel {
  @Override
  public String fetchUrl(String link) {
    URL url = null;
    try {
      url = new URL(link);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Failed to fetch data from URL");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to get meaningful data from URL");
    }
    return output.toString();
  }
}
