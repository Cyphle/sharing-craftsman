package fr.knowledge.infra.events.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.comments.events.CommentUpdatedEvent;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.Id;

public class CommentUpdatedInfraEvent {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "content")
  private String content;

  public CommentUpdatedInfraEvent() {
  }

  public CommentUpdatedInfraEvent(String id, String content) {
    this.id = id;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public CommentUpdatedEvent fromInfraToDomain() {
    return new CommentUpdatedEvent(Id.of(id), Content.of(content));
  }

  public static CommentUpdatedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new CommentUpdatedInfraEvent(event.getAggregateId(), ((CommentUpdatedEvent) event).getContentContent());
  }
}
