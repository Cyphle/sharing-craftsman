package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.AddKnowledgeCommand;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;

class AddKnowledgeCommandHandler implements CommandHandler {
  private final IdGenerator idGenerator;
  private final CategoryRepository categoryRepository;

  public AddKnowledgeCommandHandler(IdGenerator idGenerator, CategoryRepository categoryRepository) {
    this.idGenerator = idGenerator;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void handle(DomainCommand command) throws CategoryNotFoundException, AddKnowledgeException {
    Category category = categoryRepository.get(((AddKnowledgeCommand) command).getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);

    category.addKnowledge(Knowledge.of(
            idGenerator.generate(),
            ((AddKnowledgeCommand) command).getCreator(),
            ((AddKnowledgeCommand) command).getTitle(),
            ((AddKnowledgeCommand) command).getContent())
    );
    categoryRepository.save(category);
  }
}
