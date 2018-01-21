package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.valueobjects.Id;

public class AddKnowledgeCommand {
  private String id;
  private final String creator;
  private final String title;
  private final String content;

  public AddKnowledgeCommand(String id, String creator, String title, String content) {
    this.id = id;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }

  public Id getAggregateId() {
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
