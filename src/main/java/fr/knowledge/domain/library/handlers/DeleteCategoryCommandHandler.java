package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.DeleteCategoryCommand;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;

public class DeleteCategoryCommandHandler implements CommandHandler {
  private final CategoryRepository categoryRepository;

  public DeleteCategoryCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void handle(DomainCommand command) throws CategoryNotFoundException {
    Category category = categoryRepository.get(((DeleteCategoryCommand) command).getId())
            .orElseThrow(CategoryNotFoundException::new);
    category.delete();
    categoryRepository.save(category);
  }
}
