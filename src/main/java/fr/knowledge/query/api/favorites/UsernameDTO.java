package fr.knowledge.query.api.favorites;

public class UsernameDTO {
  private String username;

  public UsernameDTO() {
  }

  public UsernameDTO(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public static UsernameDTO of(String username) {
    return new UsernameDTO(username);
  }
}
