package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;

import java.util.Objects;

public class AddKnowledgeCommand implements DomainCommand {
  private final String id;
  private final String creator;
  private final String title;
  private final String content;

  public AddKnowledgeCommand(String categoryId, String creator, String title, String content) {
    this.id = categoryId;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }

  public Id getCategoryId() {
    return Id.of(id);
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
    AddKnowledgeCommand that = (AddKnowledgeCommand) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(creator, that.creator) &&
            Objects.equals(title, that.title) &&
            Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, creator, title, content);
  }
}
