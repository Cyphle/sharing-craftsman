package fr.knowledge.domain.comments.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

public class CommentAddedEvent implements DomainEvent {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CommentAddedEvent that = (CommentAddedEvent) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (commenter != null ? !commenter.equals(that.commenter) : that.commenter != null) return false;
    if (contentType != that.contentType) return false;
    if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
    return content != null ? content.equals(that.content) : that.content == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (commenter != null ? commenter.hashCode() : 0);
    result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
    result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CommentAddedEvent{" +
            "id=" + id +
            ", commenter=" + commenter +
            ", contentType=" + contentType +
            ", contentId=" + contentId +
            ", content=" + content +
            '}';
  }
}
