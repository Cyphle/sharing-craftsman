package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class KnowledgeAddedEvent implements DomainEvent<Category> {
  private final Id categoryId;
  private final Knowledge knowledge;

  public KnowledgeAddedEvent(Id categoryId, Knowledge knowledge) {
    this.categoryId = categoryId;
    this.knowledge = knowledge;
  }

  @Override
  public String getAggregateId() {
    return categoryId.getId();
  }

  @Override
  public Category apply(Category aggregate) {
    return aggregate.apply(this);
  }

  public Knowledge getKnowledge() {
    return knowledge;
  }

  public Id getKnowledgeId() {
    return knowledge.getId();
  }

  public String getKnowledgeIdContent() {
    return getKnowledgeId().getId();
  }

  public String getCreatorContent() {
    return knowledge.getCreatorContent();
  }

  public String getTitleContent() {
    return knowledge.getTitleContent();
  }

  public String getContentContent() {
    return knowledge.getContentContent();
  }
}
