package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.exceptions.AlreadyExistingCategoryException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Name;

import java.util.List;

public class CreateCategoryCommandHandler implements CommandHandler {
  private final IdGenerator idGenerator;
  private final CategoryRepository categoryRepository;

  public CreateCategoryCommandHandler(IdGenerator idGenerator, CategoryRepository categoryRepository) {
    this.idGenerator = idGenerator;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void handle(DomainCommand command) throws AlreadyExistingCategoryException, CreateCategoryException {
    verifyCategoryDoesNotExists((CreateCategoryCommand) command);
    Category newCategory = Category.newCategory(idGenerator.generate(), ((CreateCategoryCommand) command).getCategoryName());
    categoryRepository.save(newCategory);
  }

  private void verifyCategoryDoesNotExists(CreateCategoryCommand command) throws AlreadyExistingCategoryException {
    List<Category> categories = categoryRepository.getAll();
    boolean doesCategoryExists = categories.stream()
            .anyMatch(category -> category.getName().equals(Name.of(command.getCategoryName())));
    if (doesCategoryExists)
      throw new AlreadyExistingCategoryException();
  }
}
