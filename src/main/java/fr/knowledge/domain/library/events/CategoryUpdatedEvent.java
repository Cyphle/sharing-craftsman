package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.valueobjects.Name;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CategoryUpdatedEvent implements DomainEvent<Category> {
  private final Id id;
  private final Name newName;

  public CategoryUpdatedEvent(Id id, Name newName) {
    this.id = id;
    this.newName = newName;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Category apply(Category aggregate) {
    return aggregate.apply(this);
  }

  public Name getNewName() {
    return newName;
  }

  public String getNewNameContent() {
    return newName.getName();
  }
}
