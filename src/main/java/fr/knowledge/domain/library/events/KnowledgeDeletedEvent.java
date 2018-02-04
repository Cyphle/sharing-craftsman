package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;

public class KnowledgeDeletedEvent implements DomainEvent {
  private final Id categoryId;
  private final Id knowledgeId;

  public KnowledgeDeletedEvent(Id categoryId, Id knowledgeId) {
    this.categoryId = categoryId;
    this.knowledgeId = knowledgeId;
  }

  @Override
  public String getAggregateId() {
    return categoryId.getId();
  }

  public Id getKnowledgeId() {
    return knowledgeId;
  }

  public String getKnowledgeIdContent() {
    return knowledgeId.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    KnowledgeDeletedEvent that = (KnowledgeDeletedEvent) o;

    if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
    return knowledgeId != null ? knowledgeId.equals(that.knowledgeId) : that.knowledgeId == null;
  }

  @Override
  public int hashCode() {
    int result = categoryId != null ? categoryId.hashCode() : 0;
    result = 31 * result + (knowledgeId != null ? knowledgeId.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "KnowledgeDeletedEvent{" +
            "categoryId=" + categoryId +
            ", knowledgeId=" + knowledgeId +
            '}';
  }
}
