package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Name;

import java.util.List;

public class CreateCategoryCommandHandler {
  private IdGenerator idGenerator;
  private CategoryRepository categoryRepository;

  public CreateCategoryCommandHandler(IdGenerator idGenerator, CategoryRepository categoryRepository) {
    this.idGenerator = idGenerator;
    this.categoryRepository = categoryRepository;
  }

  public void handle(CreateCategoryCommand command) throws CreateCategoryException {
    verifyCategoryDoesNotExists(command);
    Category newCategory = Category.newCategory(idGenerator.generate(), command.getCategoryName());
    categoryRepository.save(newCategory);
  }

  private void verifyCategoryDoesNotExists(CreateCategoryCommand command) throws CreateCategoryException {
    List<Category> categories = categoryRepository.getAll();
    boolean doesCategoryExists = categories.stream()
            .anyMatch(category -> category.getName().equals(Name.of(command.getCategoryName())));
    if (doesCategoryExists)
      throw new CreateCategoryException("Category already exists");
  }
}
