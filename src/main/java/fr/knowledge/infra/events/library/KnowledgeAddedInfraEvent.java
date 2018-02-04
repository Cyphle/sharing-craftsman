package fr.knowledge.infra.events.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.KnowledgeAddedEvent;
import fr.knowledge.domain.library.valueobjects.Knowledge;

public class KnowledgeAddedInfraEvent {
  private String categoryId;
  private String knowledgeId;
  private String creator;
  private String title;
  private String content;

  public KnowledgeAddedInfraEvent() {
  }

  public KnowledgeAddedInfraEvent(String categoryId, String knowledgeId, String creator, String title, String content) {
    this.categoryId = categoryId;
    this.knowledgeId = knowledgeId;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public String getKnowledgeId() {
    return knowledgeId;
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

  public KnowledgeAddedEvent fromInfraToDomain() {
    return new KnowledgeAddedEvent(Id.of(categoryId), Knowledge.of(knowledgeId, creator, title, content));
  }

  public static KnowledgeAddedInfraEvent fromDomainToInfra(DomainEvent domainEvent) {
    return new KnowledgeAddedInfraEvent(
            domainEvent.getAggregateId(),
            ((KnowledgeAddedEvent) domainEvent).getKnowledgeIdContent(),
            ((KnowledgeAddedEvent) domainEvent).getCreatorContent(),
            ((KnowledgeAddedEvent) domainEvent).getTitleContent(),
            ((KnowledgeAddedEvent) domainEvent).getContentContent()
    );
  }
}
