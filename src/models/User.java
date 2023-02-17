package models;

public class User {
  private String userName;
  private int id;

  public User(String userName, int id) {
    this.userName = userName;
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public int getUserId() {
    return id;
  }
}
