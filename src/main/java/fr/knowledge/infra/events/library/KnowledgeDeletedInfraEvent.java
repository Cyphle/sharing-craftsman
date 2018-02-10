package fr.knowledge.infra.events.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.KnowledgeDeletedEvent;

public class KnowledgeDeletedInfraEvent {
  private String categoryId;
  private String knowledgeId;

  public KnowledgeDeletedInfraEvent() {
  }

  public KnowledgeDeletedInfraEvent(String categoryId, String knowledgeId) {
    this.categoryId = categoryId;
    this.knowledgeId = knowledgeId;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public String getKnowledgeId() {
    return knowledgeId;
  }

  public KnowledgeDeletedEvent fromInfraToDomain() {
    return new KnowledgeDeletedEvent(Id.of(categoryId), Id.of(knowledgeId));
  }

  public static KnowledgeDeletedInfraEvent fromDomainToInfra(DomainEvent domainEvent) {
    return new KnowledgeDeletedInfraEvent(domainEvent.getAggregateId(), ((KnowledgeDeletedEvent) domainEvent).getKnowledgeIdContent());
  }
}
