package fr.knowledge.domain.favorites.commands;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

public class RemoveFromMySelectionCommand {
  private final String id;
  private final String username;

  public RemoveFromMySelectionCommand(String id, String username) {
    this.id = id;
    this.username = username;
  }

  public Id getId() {
    return Id.of(id);
  }

  public Username getUsername() {
    return Username.from(username);
  }
}
