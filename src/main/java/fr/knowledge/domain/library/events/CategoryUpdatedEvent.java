package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.valueobjects.Name;

public class CategoryUpdatedEvent implements DomainEvent<Category> {
  private Id id;
  private Name newName;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CategoryUpdatedEvent that = (CategoryUpdatedEvent) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    return newName != null ? newName.equals(that.newName) : that.newName == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (newName != null ? newName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CategoryUpdatedEvent{" +
            "id=" + id +
            ", newName=" + newName +
            '}';
  }
}
