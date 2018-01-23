package fr.knowledge.domain.scores.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.Mark;

public class ScoreCreatedEvent implements DomainEvent {
  private final Id id;
  private final Username giver;
  private final ContentType contentType;
  private final Id contentId;
  private final Mark mark;

  public ScoreCreatedEvent(Id id, Username giver, ContentType contentType, Id contentId, Mark mark) {
    this.id = id;
    this.giver = giver;
    this.contentType = contentType;
    this.contentId = contentId;
    this.mark = mark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ScoreCreatedEvent that = (ScoreCreatedEvent) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (giver != null ? !giver.equals(that.giver) : that.giver != null) return false;
    if (contentType != that.contentType) return false;
    if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
    return mark == that.mark;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (giver != null ? giver.hashCode() : 0);
    result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
    result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
    result = 31 * result + (mark != null ? mark.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ScoreCreatedEvent{" +
            "id=" + id +
            ", giver=" + giver +
            ", contentType=" + contentType +
            ", contentId=" + contentId +
            ", mark=" + mark +
            '}';
  }
}
