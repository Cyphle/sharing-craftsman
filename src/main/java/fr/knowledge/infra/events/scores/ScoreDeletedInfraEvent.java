package fr.knowledge.infra.events.scores;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.events.ScoreDeletedEvent;

public class ScoreDeletedInfraEvent {
  @JsonProperty(value = "id")
  private String id;

  public ScoreDeletedInfraEvent() {
  }

  public ScoreDeletedInfraEvent(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DomainEvent fromInfraToDomain() {
    return new ScoreDeletedEvent(Id.of(id));
  }

  public static ScoreDeletedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new ScoreDeletedInfraEvent(event.getAggregateId());
  }
}
