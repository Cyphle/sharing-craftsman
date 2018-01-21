package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.DeleteCategoryCommand;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;

public class DeleteCategoryCommandHandler {
  private CategoryRepository categoryRepository;

  public DeleteCategoryCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void handle(DeleteCategoryCommand command) throws CategoryNotFoundException {
    Category category = categoryRepository.get(command.getId())
            .orElseThrow(CategoryNotFoundException::new);
    category.delete();
    categoryRepository.save(category);
  }
}
