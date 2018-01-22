package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.valueobjects.Id;

public class DeleteKnowledgeCommand {
  private final String categoryId;
  private final String id;

  public DeleteKnowledgeCommand(String categoryId, String id) {
    this.categoryId = categoryId;
    this.id = id;
  }

  public Id getCategoryId() {
    return Id.of(categoryId);
  }

  public Id getKnowledgeId() {
    return Id.of(id);
  }
}
