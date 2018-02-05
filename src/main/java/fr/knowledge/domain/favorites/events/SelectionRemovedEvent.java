package fr.knowledge.domain.favorites.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.favorites.aggregates.Selection;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class SelectionRemovedEvent implements DomainEvent<Selection> {
  private final Id id;

  public SelectionRemovedEvent(Id id) {
    this.id = id;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Selection apply(Selection aggregate) {
    return aggregate.apply(this);
  }
}
