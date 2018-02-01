package fr.knowledge.domain.favorites.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

import java.util.Objects;

public class RemoveFromMySelectionCommand implements DomainCommand {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RemoveFromMySelectionCommand that = (RemoveFromMySelectionCommand) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, username);
  }
}
