package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.valueobjects.Name;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
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
}
