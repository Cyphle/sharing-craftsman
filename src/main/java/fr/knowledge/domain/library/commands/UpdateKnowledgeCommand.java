package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;

import java.util.Objects;

public class UpdateKnowledgeCommand implements DomainCommand {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateKnowledgeCommand command = (UpdateKnowledgeCommand) o;
    return Objects.equals(categoryId, command.categoryId) &&
            Objects.equals(knowledgeId, command.knowledgeId) &&
            Objects.equals(creator, command.creator) &&
            Objects.equals(title, command.title) &&
            Objects.equals(content, command.content);
  }

  @Override
  public int hashCode() {

    return Objects.hash(categoryId, knowledgeId, creator, title, content);
  }
}
