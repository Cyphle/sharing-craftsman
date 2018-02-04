package fr.knowledge.domain.scores.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.valueobjects.Mark;

public class ScoreUpdatedEvent implements DomainEvent {
  private final Id id;
  private final Mark mark;

  public ScoreUpdatedEvent(Id id, Mark mark) {
    this.id = id;
    this.mark = mark;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  public Mark getMark() {
    return mark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ScoreUpdatedEvent that = (ScoreUpdatedEvent) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    return mark == that.mark;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (mark != null ? mark.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ScoreUpdatedEvent{" +
            "id=" + id +
            ", mark=" + mark +
            '}';
  }
}
