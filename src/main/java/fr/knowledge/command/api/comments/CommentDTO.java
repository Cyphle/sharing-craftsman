package fr.knowledge.command.api.comments;

public class CommentDTO {
  private String id;
  private String commenter;
  private String contentType;
  private String contentId;
  private String content;

  public CommentDTO() {
  }

  private CommentDTO(String commenter, String contentType, String contentId, String content) {
    this.commenter = commenter;
    this.contentType = contentType;
    this.contentId = contentId;
    this.content = content;
  }

  private CommentDTO(String id, String commenter, String content) {
    this.id = id;
    this.commenter = commenter;
    this.content = content;
  }

  private CommentDTO(String id, String commenter) {
    this.id = id;
    this.commenter = commenter;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCommenter() {
    return commenter;
  }

  public void setCommenter(String commenter) {
    this.commenter = commenter;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public static CommentDTO from(String commenter, String contentType, String contentId, String content) {
    return new CommentDTO(commenter, contentType, contentId, content);
  }

  public static CommentDTO from(String id, String commenter, String content) {
    return new CommentDTO(id, commenter, content);
  }

  public static CommentDTO from(String id, String commenter) {
    return new CommentDTO(id, commenter);
  }
}
