package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.ports.CategoryRepository;

import java.util.List;

public class CreateCategoryCommandHandler {
  private IdGenerator idGenerator;
  private CategoryRepository categoryRepository;

  public CreateCategoryCommandHandler(IdGenerator idGenerator, CategoryRepository categoryRepository) {
    this.idGenerator = idGenerator;
    this.categoryRepository = categoryRepository;
  }

  public void handle(CreateCategoryCommand command) {
    List<Category> categories = categoryRepository.getAll();
    Category newCategory = Category.newCategory(idGenerator.generate(), command.getCategoryName());
    categoryRepository.save(newCategory);
  }
}
