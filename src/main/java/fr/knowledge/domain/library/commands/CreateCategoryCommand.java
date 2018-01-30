package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;

public class CreateCategoryCommand implements DomainCommand {
  private final String categoryName;

  public CreateCategoryCommand(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryName() {
    return categoryName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CreateCategoryCommand that = (CreateCategoryCommand) o;

    return categoryName != null ? categoryName.equals(that.categoryName) : that.categoryName == null;
  }

  @Override
  public int hashCode() {
    return categoryName != null ? categoryName.hashCode() : 0;
  }
}
