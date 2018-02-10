package fr.knowledge.infra.events.scores;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.domain.scores.valueobjects.Mark;

public class ScoreCreatedInfraEvent {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "giver")
  private String giver;
  @JsonProperty(value = "contentType")
  private String contentType;
  @JsonProperty(value = "contentId")
  private String contentId;
  @JsonProperty(value = "mark")
  private int mark;

  public ScoreCreatedInfraEvent() {
  }

  public ScoreCreatedInfraEvent(String id, String giver, String contentType, String contentId, int mark) {
    this.id = id;
    this.giver = giver;
    this.contentType = contentType;
    this.contentId = contentId;
    this.mark = mark;
  }

  public String getId() {
    return id;
  }

  public String getGiver() {
    return giver;
  }

  public String getContentType() {
    return contentType;
  }

  public String getContentId() {
    return contentId;
  }

  public int getMark() {
    return mark;
  }

  public DomainEvent fromInfraToDomain() {
    return new ScoreCreatedEvent(Id.of(id), Username.from(giver), ContentType.valueOf(contentType), Id.of(contentId), Mark.findByValue(mark));
  }

  public static ScoreCreatedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new ScoreCreatedInfraEvent(event.getAggregateId(), ((ScoreCreatedEvent) event).getGiverContent(), ((ScoreCreatedEvent) event).getContentType().name(), ((ScoreCreatedEvent) event).getContentIdContent(), ((ScoreCreatedEvent) event).getMark().value);
  }
}
