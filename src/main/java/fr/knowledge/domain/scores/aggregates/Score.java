package fr.knowledge.domain.scores.aggregates;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.domain.scores.events.ScoreDeletedEvent;
import fr.knowledge.domain.scores.events.ScoreUpdatedEvent;

import java.util.ArrayList;
import java.util.List;

public class Score {
  private final Id id;
  private final Username giver;
  private final ContentType contentType;
  private final Id contentId;
  private Mark mark;
  private List<DomainEvent> events;

  private Score(Id id, Username giver, ContentType contentType, Id contentId, Mark mark) {
    this.id = id;
    this.giver = giver;
    this.contentType = contentType;
    this.contentId = contentId;
    this.mark = mark;
    this.events = new ArrayList<>();
  }

  public void update(Mark mark) {
    ScoreUpdatedEvent event = new ScoreUpdatedEvent(id, mark);
    apply(event);
  }

  public void delete() {
    ScoreDeletedEvent event = new ScoreDeletedEvent(id);
    apply(event);
  }

  private void apply(ScoreUpdatedEvent event) {
    mark = event.getMark();
    saveChanges(event);
  }

  private void apply(ScoreDeletedEvent event) {
    saveChanges(event);
  }

  public void saveChanges(DomainEvent event) {
    events.add(event);
  }

  public static Score of(String id, String giver, ContentType contentType, String contentId, Mark mark) {
    return new Score(Id.of(id), Username.from(giver), contentType, Id.of(contentId), mark);
  }

  public static Score newScore(String id, String giver, ContentType contentType, String contentId, Mark mark) {
    Score score = Score.of(id, giver, contentType, contentId, mark);
    score.saveChanges(new ScoreCreatedEvent(Id.of(id), Username.from(giver), contentType, Id.of(contentId), mark));
    return score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Score score = (Score) o;

    if (id != null ? !id.equals(score.id) : score.id != null) return false;
    if (giver != null ? !giver.equals(score.giver) : score.giver != null) return false;
    if (contentType != score.contentType) return false;
    if (contentId != null ? !contentId.equals(score.contentId) : score.contentId != null) return false;
    if (mark != score.mark) return false;
    return events != null ? events.equals(score.events) : score.events == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (giver != null ? giver.hashCode() : 0);
    result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
    result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
    result = 31 * result + (mark != null ? mark.hashCode() : 0);
    result = 31 * result + (events != null ? events.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Score{" +
            "id=" + id +
            ", giver=" + giver +
            ", contentType=" + contentType +
            ", contentId=" + contentId +
            ", mark=" + mark +
            ", events=" + events +
            '}';
  }
}
