package fr.knowledge.domain.comments.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.Id;

public class CommentUpdatedEvent implements DomainEvent {
  private final Id id;
  private final Content newContent;

  public CommentUpdatedEvent(Id id, Content newContent) {
    this.id = id;
    this.newContent = newContent;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  public Content getContent() {
    return newContent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CommentUpdatedEvent that = (CommentUpdatedEvent) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    return newContent != null ? newContent.equals(that.newContent) : that.newContent == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (newContent != null ? newContent.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CommentUpdatedEvent{" +
            "id=" + id +
            ", newContent=" + newContent +
            '}';
  }
}
