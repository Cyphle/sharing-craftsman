package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.DeleteKnowledgeCommand;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;

public class DeleteKnowledgeCommandHandler implements CommandHandler {
  private final CategoryRepository categoryRepository;

  public DeleteKnowledgeCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void handle(DomainCommand command) throws CategoryNotFoundException, KnowledgeNotFoundException {
    Category category = categoryRepository.get(((DeleteKnowledgeCommand) command).getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);
    category.deleteKnowledge(((DeleteKnowledgeCommand) command).getKnowledgeId());
    categoryRepository.save(category);
  }
}
