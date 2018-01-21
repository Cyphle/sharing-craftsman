package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.AddKnowledgeCommand;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;

class AddKnowledgeCommandHandler {
  private final IdGenerator idGenerator;
  private final CategoryRepository categoryRepository;

  public AddKnowledgeCommandHandler(IdGenerator idGenerator, CategoryRepository categoryRepository) {
    this.idGenerator = idGenerator;
    this.categoryRepository = categoryRepository;
  }

  public void handle(AddKnowledgeCommand command) throws CategoryNotFoundException, AddKnowledgeException {
    Category category = categoryRepository.get(command.getAggregateId())
            .orElseThrow(CategoryNotFoundException::new);

    category.addKnowledge(Knowledge.of(idGenerator.generate(), command.getCreator(), command.getTitle(), command.getContent()));
    categoryRepository.save(category);
  }
}
