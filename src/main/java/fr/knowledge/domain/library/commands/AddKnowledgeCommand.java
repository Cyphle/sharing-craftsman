package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
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
}
