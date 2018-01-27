package fr.knowledge.domain.comments.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;

public class CommentDeletedEvent implements DomainEvent {
  private Id id;

  public CommentDeletedEvent(Id id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CommentDeletedEvent that = (CommentDeletedEvent) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "CommentDeletedEvent{" +
            "id=" + id +
            '}';
  }
}
