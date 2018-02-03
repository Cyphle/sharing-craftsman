package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.UpdateKnowledgeCommand;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;

class UpdateKnowledgeCommandHandler implements CommandHandler {
  private final CategoryRepository categoryRepository;

  public UpdateKnowledgeCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void handle(DomainCommand command) throws CategoryNotFoundException, AddKnowledgeException, KnowledgeNotFoundException {
    Category category = categoryRepository.get(((UpdateKnowledgeCommand) command).getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);
    category.updateKnowledge(Knowledge.of(
            ((UpdateKnowledgeCommand) command).getId(),
            ((UpdateKnowledgeCommand) command).getCreator(),
            ((UpdateKnowledgeCommand) command).getTitle(),
            ((UpdateKnowledgeCommand) command).getContent())
    );
    categoryRepository.save(category);
  }
}
