package fr.knowledge.domain.comments.events;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CommentAddedEvent implements DomainEvent<Comment> {
  private final Id id;
  private final Username commenter;
  private final ContentType contentType;
  private final Id contentId;
  private final Content content;

  public CommentAddedEvent(Id id, Username commenter, ContentType contentType, Id contentId, Content content) {
    this.id = id;
    this.commenter = commenter;
    this.contentType = contentType;
    this.contentId = contentId;
    this.content = content;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  public Id getId() {
    return id;
  }

  public Username getCommenter() {
    return commenter;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public Id getContentId() {
    return contentId;
  }

  public Content getContent() {
    return content;
  }

  @Override
  public Comment apply(Comment aggregate) {
    return aggregate.apply(this);
  }
}
