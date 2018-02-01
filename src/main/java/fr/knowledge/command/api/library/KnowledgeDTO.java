package fr.knowledge.command.api.library;

public class KnowledgeDTO {
  private String categoryId;
  private String creator;
  private String title;
  private String content;

  public KnowledgeDTO() {
  }

  public KnowledgeDTO(String categoryId, String creator, String title, String content) {
    this.categoryId = categoryId;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public static KnowledgeDTO from(String categoryId, String creator, String title, String content) {
    return new KnowledgeDTO(categoryId, creator, title, content);
  }
}
