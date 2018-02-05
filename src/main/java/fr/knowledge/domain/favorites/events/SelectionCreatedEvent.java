package fr.knowledge.domain.favorites.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.aggregates.Selection;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class SelectionCreatedEvent implements DomainEvent<Selection> {
  private final Id id;
  private final Username username;
  private final ContentType contentType;
  private final Id contentId;

  public SelectionCreatedEvent(Id id, Username username, ContentType contentType, Id contentId) {
    this.id = id;
    this.username = username;
    this.contentType = contentType;
    this.contentId = contentId;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  public Id getId() {
    return id;
  }

  public Username getUsername() {
    return username;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public Id getContentId() {
    return contentId;
  }

  @Override
  public Selection apply(Selection aggregate) {
    return aggregate.apply(this);
  }
}
