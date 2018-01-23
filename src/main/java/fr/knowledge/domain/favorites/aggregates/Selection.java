package fr.knowledge.domain.favorites.aggregates;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.events.SelectionCreatedEvent;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;

import java.util.ArrayList;
import java.util.List;

public class Selection {
  private Id id;
  private final Username username;
  private final ContentType contentType;
  private final Id contentId;
  private List<DomainEvent> changes;

  private Selection(Id id, Username username, ContentType contentType, Id contentId) {
    this.id = id;
    this.username = username;
    this.contentType = contentType;
    this.contentId = contentId;
    this.changes = new ArrayList<>();
  }

  public void delete() {
    SelectionRemovedEvent event = new SelectionRemovedEvent(id);
    apply(event);
  }

  private void apply(SelectionRemovedEvent event) {
    saveChanges(event);
  }

  public void saveChanges(DomainEvent event) {
    changes.add(event);
  }

  public static Selection of(String id, String username, ContentType contentType, String contentId) {
    return new Selection(Id.of(id), Username.from(username), contentType, Id.of(contentId));
  }

  public static Selection newSelection(String id, String username, ContentType contentType, String contentId) {
    Selection selection = Selection.of(id, username, contentType, contentId);
    selection.saveChanges(new SelectionCreatedEvent(Id.of(id), Username.from(username), contentType, Id.of(contentId)));
    return selection;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Selection selection = (Selection) o;

    if (id != null ? !id.equals(selection.id) : selection.id != null) return false;
    if (username != null ? !username.equals(selection.username) : selection.username != null) return false;
    if (contentType != selection.contentType) return false;
    if (contentId != null ? !contentId.equals(selection.contentId) : selection.contentId != null) return false;
    return changes != null ? changes.equals(selection.changes) : selection.changes == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
    result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
    result = 31 * result + (changes != null ? changes.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Selection{" +
            "id=" + id +
            ", username=" + username +
            ", contentType=" + contentType +
            ", contentId=" + contentId +
            ", changes=" + changes +
            '}';
  }
}
