package fr.knowledge.domain.scores.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.valueobjects.Mark;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ScoreCreatedEvent implements DomainEvent<Score> {
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
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Score apply(Score aggregate) {
    return aggregate.apply(this);
  }

  public Id getId() {
    return id;
  }

  public Username getGiver() {
    return giver;
  }

  public String getGiverContent() {
    return giver.getUsername();
  }

  public ContentType getContentType() {
    return contentType;
  }

  public Id getContentId() {
    return contentId;
  }

  public String getContentIdContent() {
    return contentId.getId();
  }

  public Mark getMark() {
    return mark;
  }
}
