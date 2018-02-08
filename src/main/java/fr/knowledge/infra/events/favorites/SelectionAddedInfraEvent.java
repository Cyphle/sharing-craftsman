package fr.knowledge.infra.events.favorites;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.events.SelectionAddedEvent;

public class SelectionAddedInfraEvent {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "username")
  private String username;
  @JsonProperty(value = "contentType")
  private String contentType;
  @JsonProperty(value = "contentId")
  private String contentId;

  public SelectionAddedInfraEvent() {
  }

  public SelectionAddedInfraEvent(String id, String username, String contentType, String contentId) {
    this.id = id;
    this.username = username;
    this.contentType = contentType;
    this.contentId = contentId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public SelectionAddedEvent fromInfraToDomain() {
    return new SelectionAddedEvent(Id.of(id), Username.from(username), ContentType.valueOf(contentType), Id.of(contentId));
  }

  public static SelectionAddedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new SelectionAddedInfraEvent(event.getAggregateId(), ((SelectionAddedEvent) event).getUsernameContent(), ((SelectionAddedEvent) event).getContentType().name(), ((SelectionAddedEvent) event).getContentIdContent());
  }
}
