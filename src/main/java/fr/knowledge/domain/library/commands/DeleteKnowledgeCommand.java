package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;

import java.util.Objects;

public class DeleteKnowledgeCommand implements DomainCommand {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeleteKnowledgeCommand that = (DeleteKnowledgeCommand) o;
    return Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(categoryId, id);
  }
}
