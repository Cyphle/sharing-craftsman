package fr.knowledge.domain.comments.aggregates;

import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.comments.events.CommentDeletedEvent;
import fr.knowledge.domain.comments.events.CommentUpdatedEvent;
import fr.knowledge.domain.comments.exceptions.UpdateCommentException;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class Comment {
  private Id id;
  private Username commenter;
  private ContentType contentType;
  private Id contentId;
  private Content content;
  private List<DomainEvent> events;
  private boolean deleted;

  private Comment() { }

  private Comment(Id id, Username commenter, ContentType contentType, Id contentId, Content content) {
    CommentAddedEvent event = new CommentAddedEvent(id, commenter, contentType, contentId, content);
    apply(event);
    saveChanges(event);
  }

  public void update(Content newContent) throws UpdateCommentException {
    verifyContent(newContent);
    CommentUpdatedEvent event = new CommentUpdatedEvent(id, newContent);
    apply(event);
    saveChanges(event);
  }

  public void delete() {
    CommentDeletedEvent event = new CommentDeletedEvent(id);
    apply(event);
    saveChanges(event);
  }

  public void saveChanges(DomainEvent event) {
    events.add(event);
  }

  public Comment apply(CommentAddedEvent event) {
    this.id = event.getId();
    this.commenter = event.getCommenter();
    this.contentType = event.getContentType();
    this.contentId = event.getContentId();
    this.content = event.getContent();
    this.deleted = false;
    events = new ArrayList<>();
    return this;
  }

  public Comment apply(CommentUpdatedEvent event) {
    content = event.getContent();
    return this;
  }

  public Comment apply(CommentDeletedEvent event) {
    deleted = true;
    return this;
  }

  private void verifyContent(Content newContent) throws UpdateCommentException {
    if (newContent.isEmpty())
      throw new UpdateCommentException("Content cannot be empty.");
  }

  public static Comment of(String id, String commenter, ContentType contentType, String contentId, String content) {
    Comment comment = new Comment();
    comment.apply(new CommentAddedEvent(Id.of(id), Username.from(commenter), contentType, Id.of(contentId), Content.of(content)));
    return comment;
  }

  public static Comment newComment(String id, String commenter, ContentType contentType, String contentId, String content) {
    return new Comment(Id.of(id), Username.from(commenter), contentType, Id.of(contentId), Content.of(content));
  }
}
