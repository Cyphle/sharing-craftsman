package fr.knowledge.command.api.favorites;

public class SelectionDTO {
  private String username;
  private String contentType;
  private String id;

  public SelectionDTO() {
  }

  public SelectionDTO(String username, String contentType, String id) {
    this.username = username;
    this.contentType = contentType;
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public static SelectionDTO from(String username, String contentType, String id) {
    return new SelectionDTO(username, contentType, id);
  }
}
