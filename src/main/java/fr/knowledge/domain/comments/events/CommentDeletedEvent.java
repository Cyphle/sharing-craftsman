package fr.knowledge.domain.comments.events;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CommentDeletedEvent implements DomainEvent<Comment> {
  private final Id id;

  public CommentDeletedEvent(Id id) {
    this.id = id;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Comment apply(Comment aggregate) {
    return aggregate.apply(this);
  }
}
