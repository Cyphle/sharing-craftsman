package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.exceptions.CategoryException;
import fr.knowledge.domain.library.commands.UpdateCategoryCommand;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;

public class UpdateCategoryCommandHandler implements CommandHandler {
  private final CategoryRepository categoryRepository;

  public UpdateCategoryCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void handle(DomainCommand command) throws CategoryNotFoundException, CategoryException {
    Category category = categoryRepository.get(((UpdateCategoryCommand) command).getId())
            .orElseThrow(CategoryNotFoundException::new);
    category.update(((UpdateCategoryCommand) command).getNewName());
    categoryRepository.save(category);
  }
}
