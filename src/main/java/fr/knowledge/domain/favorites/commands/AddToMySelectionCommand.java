package fr.knowledge.domain.favorites.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.ContentType;

import java.util.Objects;

public class AddToMySelectionCommand implements DomainCommand {
  private final String username;
  private final ContentType contentType;
  private final String contentId;

  public AddToMySelectionCommand(String username, ContentType contentType, String contentId) {
    this.username = username;
    this.contentType = contentType;
    this.contentId = contentId;
  }

  public String getUsername() {
    return username;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public String getContentId() {
    return contentId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddToMySelectionCommand that = (AddToMySelectionCommand) o;
    return Objects.equals(username, that.username) &&
            contentType == that.contentType &&
            Objects.equals(contentId, that.contentId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(username, contentType, contentId);
  }
}
