package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.valueobjects.Id;

public class CategoryDeletedEvent implements DomainEvent {
  private final Id id;

  public CategoryDeletedEvent(Id id) {
    this.id = id;
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
