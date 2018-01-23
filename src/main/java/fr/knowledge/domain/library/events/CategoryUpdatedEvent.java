package fr.knowledge.domain.library.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.valueobjects.Name;

public class CategoryUpdatedEvent implements DomainEvent {
  private final Id id;
  private final Name newName;

  public CategoryUpdatedEvent(Id id, Name newName) {
    this.id = id;
    this.newName = newName;
  }

  public Name getNewName() {
    return newName;
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
