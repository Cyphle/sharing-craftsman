package fr.knowledge.domain.comments.aggregates;

import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

import java.util.ArrayList;
import java.util.List;

public class Comment {
  private final Id id;
  private final Username commenter;
  private final ContentType contentType;
  private final Id contentId;
  private final Content content;
  private List<DomainEvent> events;

  public Comment(Id id, Username commenter, ContentType contentType, Id contentId, Content content) {
    this.id = id;
    this.commenter = commenter;
    this.contentType = contentType;
    this.contentId = contentId;
    this.content = content;
    events = new ArrayList<>();
  }

  public void saveChanges(DomainEvent event) {
    events.add(event);
  }

  public static Comment of(String id, String commenter, ContentType contentType, String contentId, String content) {
    return new Comment(Id.of(id), Username.from(commenter), contentType, Id.of(contentId), Content.of(content));
  }

  public static Comment newComment(String id, String commenter, ContentType contentType, String contentId, String content) {
    Comment comment = Comment.of(id, commenter, contentType, contentId, content);
    comment.saveChanges(new CommentAddedEvent(Id.of(id), Username.from(commenter), contentType, Id.of(contentId), Content.of(content)));
    return comment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Comment comment = (Comment) o;

    if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
    if (commenter != null ? !commenter.equals(comment.commenter) : comment.commenter != null) return false;
    if (contentType != comment.contentType) return false;
    if (contentId != null ? !contentId.equals(comment.contentId) : comment.contentId != null) return false;
    if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
    return events != null ? events.equals(comment.events) : comment.events == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (commenter != null ? commenter.hashCode() : 0);
    result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
    result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    result = 31 * result + (events != null ? events.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Comment{" +
            "id=" + id +
            ", commenter=" + commenter +
            ", contentType=" + contentType +
            ", contentId=" + contentId +
            ", content=" + content +
            ", events=" + events +
            '}';
  }
}
