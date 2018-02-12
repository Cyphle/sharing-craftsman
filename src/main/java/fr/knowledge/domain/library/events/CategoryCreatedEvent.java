package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.valueobjects.Name;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CategoryCreatedEvent implements DomainEvent<Category> {
  private final Id id;
  private final Name name;

  public CategoryCreatedEvent(Id id, Name name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Category apply(Category aggregate) {
    return aggregate.apply(this);
  }

  public Id getId() {
    return id;
  }

  public Name getName() {
    return name;
  }

  public String getNameContent() {
    return name.getName();
  }
}
