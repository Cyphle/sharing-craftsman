package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.valueobjects.Knowledge;

public class KnowledgeUpdatedEvent implements DomainEvent {
  private final Id categoryId;
  private final Knowledge updatedKnowledge;

  public KnowledgeUpdatedEvent(Id categoryId, Knowledge updatedKnowledge) {
    this.categoryId = categoryId;
    this.updatedKnowledge = updatedKnowledge;
  }

  public Id getKnowledgeId() {
    return updatedKnowledge.getId();
  }

  public Knowledge getKnowledge() {
    return updatedKnowledge;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    KnowledgeUpdatedEvent that = (KnowledgeUpdatedEvent) o;

    if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
    return updatedKnowledge != null ? updatedKnowledge.equals(that.updatedKnowledge) : that.updatedKnowledge == null;
  }

  @Override
  public int hashCode() {
    int result = categoryId != null ? categoryId.hashCode() : 0;
    result = 31 * result + (updatedKnowledge != null ? updatedKnowledge.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "KnowledgeUpdatedEvent{" +
            "categoryId=" + categoryId +
            ", updatedKnowledge=" + updatedKnowledge +
            '}';
  }
}
