package fr.knowledge.infra.events.favorites;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;

public class SelectionRemovedInfraEvent {
  @JsonProperty(value = "id")
  private String id;

  public SelectionRemovedInfraEvent() {
  }

  public SelectionRemovedInfraEvent(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SelectionRemovedEvent fromInfraToDomain() {
    return new SelectionRemovedEvent(Id.of(id));
  }

  public static SelectionRemovedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new SelectionRemovedInfraEvent(event.getAggregateId());
  }
}
