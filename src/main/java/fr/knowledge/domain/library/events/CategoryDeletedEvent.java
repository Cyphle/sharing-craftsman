package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;

public class CategoryDeletedEvent implements DomainEvent<Category> {
  private final Id id;

  public CategoryDeletedEvent(Id id) {
    this.id = id;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Category apply(Category aggregate) {
    return aggregate.apply(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CategoryDeletedEvent that = (CategoryDeletedEvent) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "CategoryDeletedEvent{" +
            "id=" + id +
            '}';
  }
}
