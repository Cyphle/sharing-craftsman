package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.valueobjects.Knowledge;

public class KnowledgeAddedEvent implements DomainEvent {
  private final Id categoryId;
  private final Knowledge knowledge;

  public KnowledgeAddedEvent(Id categoryId, Knowledge knowledge) {
    this.categoryId = categoryId;
    this.knowledge = knowledge;
  }

  public Knowledge getKnowledge() {
    return knowledge;
  }

  public Id getKnowledgeId() {
    return knowledge.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    KnowledgeAddedEvent that = (KnowledgeAddedEvent) o;

    if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
    return knowledge != null ? knowledge.equals(that.knowledge) : that.knowledge == null;
  }

  @Override
  public int hashCode() {
    int result = categoryId != null ? categoryId.hashCode() : 0;
    result = 31 * result + (knowledge != null ? knowledge.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "KnowledgeAddedEvent{" +
            "categoryId=" + categoryId +
            ", knowledge=" + knowledge +
            '}';
  }
}
