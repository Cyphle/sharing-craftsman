package fr.knowledge.domain.comments.events;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.Id;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CommentUpdatedEvent implements DomainEvent<Comment> {
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

  @Override
  public Comment apply(Comment aggregate) {
    return aggregate.apply(this);
  }

  public Content getContent() {
    return newContent;
  }
}
