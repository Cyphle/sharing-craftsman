package fr.knowledge.domain.common.valueobjects;

public class Username {
  private String username;

  public Username(String username) {
    this.username = username;
  }

  public boolean isEmpty() {
    return username.isEmpty();
  }

  public static Username from(String username) {
    return new Username(username);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Username username1 = (Username) o;

    return username != null ? username.equals(username1.username) : username1.username == null;
  }

  @Override
  public int hashCode() {
    return username != null ? username.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Username{" +
            "username='" + username + '\'' +
            '}';
  }
}
