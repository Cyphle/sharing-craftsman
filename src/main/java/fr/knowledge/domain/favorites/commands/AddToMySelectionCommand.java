package fr.knowledge.domain.favorites.commands;

import fr.knowledge.domain.favorites.SelectionType;

public class AddToMySelectionCommand {
  private final String username;
  private final SelectionType selectionType;
  private final String contentId;

  public AddToMySelectionCommand(String username, SelectionType selectionType, String contentId) {
    this.username = username;
    this.selectionType = selectionType;
    this.contentId = contentId;
  }

  public String getUsername() {
    return username;
  }

  public SelectionType getSelectionType() {
    return selectionType;
  }

  public String getContentId() {
    return contentId;
  }
}
