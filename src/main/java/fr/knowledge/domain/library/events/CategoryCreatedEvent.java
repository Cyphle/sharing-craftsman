package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.valueobjects.Name;

public class CategoryCreatedEvent implements DomainEvent<Category> {
  private Id id;
  private Name name;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CategoryCreatedEvent that = (CategoryCreatedEvent) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    return name != null ? name.equals(that.name) : that.name == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CategoryCreatedEvent{" +
            "id=" + id +
            ", name=" + name +
            '}';
  }
}
