package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.valueobjects.Name;

public class CategoryCreatedEvent implements DomainEvent {
  private final Id id;
  private final Name name;

  public CategoryCreatedEvent(Id id, Name name) {
    this.id = id;
    this.name = name;
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
