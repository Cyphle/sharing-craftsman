package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.UpdateCategoryCommand;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.domain.library.ports.CategoryRepository;

public class UpdateCategoryCommandHandler {
  private CategoryRepository categoryRepository;

  public UpdateCategoryCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void handle(UpdateCategoryCommand command) throws CategoryNotFoundException, CreateCategoryException {
    Category category = categoryRepository.get(command.getId())
            .orElseThrow(CategoryNotFoundException::new);
    category.update(command.getNewName());
    categoryRepository.save(category);
  }
}
