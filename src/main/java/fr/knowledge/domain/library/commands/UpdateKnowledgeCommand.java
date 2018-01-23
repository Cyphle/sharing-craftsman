package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.valueobjects.Id;

public class UpdateKnowledgeCommand {
  private final String categoryId;
  private final String knowledgeId;
  private final String creator;
  private final String title;
  private final String content;

  public UpdateKnowledgeCommand(String categoryId, String knowledgeId, String creator, String title, String content) {
    this.categoryId = categoryId;
    this.knowledgeId = knowledgeId;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }

  public Id getCategoryId() {
    return Id.of(categoryId);
  }

  public String getId() {
    return knowledgeId;
  }

  public String getCreator() {
    return creator;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
