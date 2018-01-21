package fr.knowledge.domain.library.commands;

public class CreateCategoryCommand {
  private String categoryName;

  public CreateCategoryCommand(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryName() {
    return categoryName;
  }
}
