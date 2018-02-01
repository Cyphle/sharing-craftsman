package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;

import java.util.Objects;

public class DeleteCategoryCommand implements DomainCommand {
  private final String id;

  public DeleteCategoryCommand(String id) {
    this.id = id;
  }

  public Id getId() {
    return Id.of(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeleteCategoryCommand command = (DeleteCategoryCommand) o;
    return Objects.equals(id, command.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}
