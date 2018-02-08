package fr.knowledge.domain.favorites.aggregates;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.events.SelectionAddedEvent;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;
import fr.knowledge.domain.favorites.exceptions.SelectionException;
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
    SelectionAddedEvent event = new SelectionAddedEvent(id, username, contentType, contentId);
    apply(event);
    saveChanges(event);
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

  public void delete(Username username) throws SelectionException {
    verifyUsername(username);
    SelectionRemovedEvent event = new SelectionRemovedEvent(id);
    apply(event);
    saveChanges(event);
  }

  public Selection apply(SelectionAddedEvent event) {
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

  private void verifyUsername(Username username) throws SelectionException {
    if (!this.username.equals(username))
      throw new SelectionException("Wrong user.");
  }

  public static Selection of(String id, String username, ContentType contentType, String contentId) {
    Selection selection = new Selection();
    selection.apply(new SelectionAddedEvent(Id.of(id), Username.from(username), contentType, Id.of(contentId)));
    return selection;
  }

  public static Selection newSelection(String id, String username, ContentType contentType, String contentId) {
    return new Selection(Id.of(id), Username.from(username), contentType, Id.of(contentId));
  }

  public static Selection rebuild(List<DomainEvent> events) {
    return events.stream()
            .reduce(new Selection(),
                    (item, event) -> (Selection) event.apply(item),
                    (item1, item2) -> item2);
  }
}
