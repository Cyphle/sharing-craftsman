package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.DeleteKnowledgeCommand;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;

class DeleteKnowledgeCommandHandler {
  private final CategoryRepository categoryRepository;

  public DeleteKnowledgeCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void handle(DeleteKnowledgeCommand command) throws CategoryNotFoundException, KnowledgeNotFoundException {
    Category category = categoryRepository.get(command.getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);
    category.deleteKnowledge(command.getKnowledgeId());
    categoryRepository.save(category);
  }
}
