package fr.knowledge.domain.scores.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.valueobjects.Mark;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ScoreUpdatedEvent implements DomainEvent<Score> {
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
  public Score apply(Score aggregate) {
    return aggregate.apply(this);
  }
}
