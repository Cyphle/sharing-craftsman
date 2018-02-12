package fr.knowledge.command.api.favorites;

public class SelectionDTO {
  private String id;
  private String username;
  private String contentType;
  private String contentId;

  public SelectionDTO() {
  }

  private SelectionDTO(String username, String contentType, String contentId) {
    this.username = username;
    this.contentType = contentType;
    this.contentId = contentId;
  }

  private SelectionDTO(String id, String username) {
    this.id = id;
    this.username = username;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public static SelectionDTO from(String username, String contentType, String contentId) {
    return new SelectionDTO(username, contentType, contentId);
  }

  public static SelectionDTO from(String id, String username) {
    return new SelectionDTO(id, username);
  }
}
