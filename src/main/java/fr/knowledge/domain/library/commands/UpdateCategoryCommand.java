package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.valueobjects.Name;

public class UpdateCategoryCommand implements DomainCommand {
  private final String id;
  private final String newName;

  public UpdateCategoryCommand(String id, String newName) {
    this.id = id;
    this.newName = newName;
  }

  public Id getId() {
    return Id.of(id);
  }

  public Name getNewName() {
    return Name.of(newName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UpdateCategoryCommand command = (UpdateCategoryCommand) o;

    if (id != null ? !id.equals(command.id) : command.id != null) return false;
    return newName != null ? newName.equals(command.newName) : command.newName == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (newName != null ? newName.hashCode() : 0);
    return result;
  }
}
