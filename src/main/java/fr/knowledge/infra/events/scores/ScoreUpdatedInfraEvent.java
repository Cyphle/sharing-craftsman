package fr.knowledge.infra.events.scores;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.events.ScoreUpdatedEvent;
import fr.knowledge.domain.scores.valueobjects.Mark;

public class ScoreUpdatedInfraEvent {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "mark")
  private int mark;

  public ScoreUpdatedInfraEvent() {
  }

  public ScoreUpdatedInfraEvent(String id, int mark) {
    this.id = id;
    this.mark = mark;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getMark() {
    return mark;
  }

  public void setMark(int mark) {
    this.mark = mark;
  }

  public DomainEvent fromInfraToDomain() {
    return new ScoreUpdatedEvent(Id.of(id), Mark.findByValue(mark));
  }

  public static ScoreUpdatedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new ScoreUpdatedInfraEvent(event.getAggregateId(), ((ScoreUpdatedEvent) event).getMark().value);
  }
}
