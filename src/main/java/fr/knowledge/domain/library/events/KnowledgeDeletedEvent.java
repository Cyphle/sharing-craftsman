package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class KnowledgeDeletedEvent implements DomainEvent<Category> {
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

  @Override
  public Category apply(Category aggregate) {
    return aggregate.apply(this);
  }

  public Id getKnowledgeId() {
    return knowledgeId;
  }

  public String getKnowledgeIdContent() {
    return knowledgeId.getId();
  }
}
