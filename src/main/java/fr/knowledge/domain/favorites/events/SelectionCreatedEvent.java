package fr.knowledge.domain.favorites.events;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.SelectionType;

public class SelectionCreatedEvent implements DomainEvent {
  private final Id id;
  private final Username username;
  private final SelectionType selectionType;
  private final Id contentId;

  public SelectionCreatedEvent(Id id, Username username, SelectionType selectionType, Id contentId) {
    this.id = id;
    this.username = username;
    this.selectionType = selectionType;
    this.contentId = contentId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SelectionCreatedEvent that = (SelectionCreatedEvent) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (username != null ? !username.equals(that.username) : that.username != null) return false;
    if (selectionType != that.selectionType) return false;
    return contentId != null ? contentId.equals(that.contentId) : that.contentId == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (selectionType != null ? selectionType.hashCode() : 0);
    result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SelectionCreatedEvent{" +
            "id=" + id +
            ", username=" + username +
            ", selectionType=" + selectionType +
            ", contentId=" + contentId +
            '}';
  }
}
