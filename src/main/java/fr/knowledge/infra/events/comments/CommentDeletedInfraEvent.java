package fr.knowledge.infra.events.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.comments.events.CommentDeletedEvent;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;

public class CommentDeletedInfraEvent {
  @JsonProperty(value = "id")
  private String id;

  public CommentDeletedInfraEvent() {
  }

  public CommentDeletedInfraEvent(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CommentDeletedEvent fromInfraToDomain() {
    return new CommentDeletedEvent(Id.of(id));
  }

  public static CommentDeletedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new CommentDeletedInfraEvent(event.getAggregateId());
  }
}
