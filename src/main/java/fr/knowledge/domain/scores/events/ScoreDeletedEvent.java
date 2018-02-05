package fr.knowledge.domain.scores.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.aggregates.Score;

public class ScoreDeletedEvent implements DomainEvent<Score> {
  private final Id id;

  public ScoreDeletedEvent(Id id) {
    this.id = id;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Score apply(Score aggregate) {
    return aggregate.apply(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ScoreDeletedEvent that = (ScoreDeletedEvent) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ScoreDeletedEvent{" +
            "id=" + id +
            '}';
  }
}
