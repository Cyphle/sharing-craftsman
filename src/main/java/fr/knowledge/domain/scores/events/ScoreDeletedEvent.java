package fr.knowledge.domain.scores.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.aggregates.Score;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
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
}
