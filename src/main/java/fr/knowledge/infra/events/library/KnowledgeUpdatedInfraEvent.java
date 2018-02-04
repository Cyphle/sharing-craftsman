package fr.knowledge.infra.events.library;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.KnowledgeUpdatedEvent;
import fr.knowledge.domain.library.valueobjects.Knowledge;

public class KnowledgeUpdatedInfraEvent {
  private String categoryId;
  private String knowledgeId;
  private String creator;
  private String title;
  private String content;

  public KnowledgeUpdatedInfraEvent() {
  }

  public KnowledgeUpdatedInfraEvent(String categoryId, String knowledgeId, String creator, String title, String content) {
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

  public KnowledgeUpdatedEvent fromInfraToDomain() {
    return new KnowledgeUpdatedEvent(Id.of(categoryId), Knowledge.of(knowledgeId, creator, title, content));
  }
}
