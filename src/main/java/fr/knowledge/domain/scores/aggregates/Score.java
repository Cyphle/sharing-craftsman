package fr.knowledge.domain.scores.aggregates;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.exceptions.ScoreException;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.domain.scores.events.ScoreDeletedEvent;
import fr.knowledge.domain.scores.events.ScoreUpdatedEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class Score {
  private Id id;
  private Username giver;
  private ContentType contentType;
  private Id contentId;
  private Mark mark;
  private List<DomainEvent> events;
  private boolean deleted;

  private Score() { }

  private Score(Id id, Username giver, ContentType contentType, Id contentId, Mark mark) {
    ScoreCreatedEvent event = new ScoreCreatedEvent(id, giver, contentType, contentId, mark);
    apply(event);
    saveChanges(event);
  }

  public void update(Username giver, Mark mark) throws ScoreException {
    verifyUser(giver);
    ScoreUpdatedEvent event = new ScoreUpdatedEvent(id, mark);
    apply(event);
    saveChanges(event);
  }

  public void delete(Username giver) throws ScoreException {
    verifyUser(giver);
    ScoreDeletedEvent event = new ScoreDeletedEvent(id);
    apply(event);
    saveChanges(event);
  }

  public Score apply(ScoreCreatedEvent event) {
    this.id = event.getId();
    this.giver = event.getGiver();
    this.contentType = event.getContentType();
    this.contentId = event.getContentId();
    this.mark = event.getMark();
    this.deleted = false;
    this.events = new ArrayList<>();
    return this;
  }

  public Score apply(ScoreUpdatedEvent event) {
    mark = event.getMark();
    return this;
  }

  public Score apply(ScoreDeletedEvent event) {
    deleted = true;
    return this;
  }

  public void saveChanges(DomainEvent event) {
    events.add(event);
  }

  private void verifyUser(Username giver) throws ScoreException {
    if (!this.giver.equals(giver))
      throw new ScoreException("Wrong user.");
  }

  public static Score of(String id, String giver, ContentType contentType, String contentId, Mark mark) {
    Score score = new Score();
    score.apply(new ScoreCreatedEvent(Id.of(id), Username.from(giver), contentType, Id.of(contentId), mark));
    return score;
  }

  public static Score newScore(String id, String giver, ContentType contentType, String contentId, Mark mark) {
    return new Score(Id.of(id), Username.from(giver), contentType, Id.of(contentId), mark);
  }

  public static Score rebuild(List<DomainEvent> events) {
    return events.stream()
            .reduce(new Score(),
                    (item, event) -> (Score) event.apply(item),
                    (item1, item2) -> item2);
  }
}
