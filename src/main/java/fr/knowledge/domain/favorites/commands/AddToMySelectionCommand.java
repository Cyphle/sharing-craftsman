package fr.knowledge.domain.favorites.commands;

import fr.knowledge.domain.common.valueobjects.ContentType;

public class AddToMySelectionCommand {
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
}
