package fr.knowledge.domain.favorites.aggregates;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.events.SelectionCreatedEvent;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class Selection {
  private Id id;
  private Username username;
  private ContentType contentType;
  private Id contentId;
  private List<DomainEvent> changes;
  private boolean deleted;

  private Selection() { }

  private Selection(Id id, Username username, ContentType contentType, Id contentId) {
    SelectionCreatedEvent event = new SelectionCreatedEvent(id, username, contentType, contentId);
    apply(event);
    saveChanges(event);
  }

  public void delete() {
    SelectionRemovedEvent event = new SelectionRemovedEvent(id);
    apply(event);
    saveChanges(event);
  }

  public Selection apply(SelectionCreatedEvent event) {
    this.id = event.getId();
    this.username = event.getUsername();
    this.contentType = event.getContentType();
    this.contentId = event.getContentId();
    this.deleted = false;
    this.changes = new ArrayList<>();
    return this;
  }

  public Selection apply(SelectionRemovedEvent event) {
    deleted = true;
    return this;
  }

  public void saveChanges(DomainEvent event) {
    changes.add(event);
  }

  public static Selection of(String id, String username, ContentType contentType, String contentId) {
    Selection selection = new Selection();
    selection.apply(new SelectionCreatedEvent(Id.of(id), Username.from(username), contentType, Id.of(contentId)));
    return selection;
  }

  public static Selection newSelection(String id, String username, ContentType contentType, String contentId) {
    return new Selection(Id.of(id), Username.from(username), contentType, Id.of(contentId));
  }
}
