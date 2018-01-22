package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.UpdateKnowledgeCommand;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;

public class UpdateKnowledgeCommandHandler {
  private CategoryRepository categoryRepository;

  public UpdateKnowledgeCommandHandler(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void handle(UpdateKnowledgeCommand command) throws CategoryNotFoundException, AddKnowledgeException, KnowledgeNotFoundException {
    Category category = categoryRepository.get(command.getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);
    category.updateKnowledge(Knowledge.of(command.getId(), command.getCreator(), command.getTitle(), command.getContent()));
    categoryRepository.save(category);
  }
}
