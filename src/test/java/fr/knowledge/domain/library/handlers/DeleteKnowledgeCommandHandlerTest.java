package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.DeleteKnowledgeCommand;
import fr.knowledge.domain.library.events.KnowledgeAddedEvent;
import fr.knowledge.domain.library.events.KnowledgeDeletedEvent;
import fr.knowledge.domain.library.exceptions.CategoryException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteKnowledgeCommandHandlerTest {
  @Mock
  private CategoryRepository categoryRepository;
  private DeleteKnowledgeCommandHandler deleteKnowledgeCommandHandler;

  @Before
  public void setUp() {
    Category category = Category.of("aaa", "Architecture");
    category.apply(new KnowledgeAddedEvent(Id.of("aaa"), Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is my content.")));
    given(categoryRepository.get(Id.of("aaa"))).willReturn(Optional.of(category));
    deleteKnowledgeCommandHandler = new DeleteKnowledgeCommandHandler(categoryRepository);
  }

  @Test
  public void should_delete_knowledge_from_category() throws Exception {
    DeleteKnowledgeCommand command = new DeleteKnowledgeCommand("aaa", "aaa");

    deleteKnowledgeCommandHandler.handle(command);

    Category updatedCategory = Category.of("aaa", "Architecture");
    updatedCategory.saveChanges(new KnowledgeDeletedEvent(Id.of("aaa"), Id.of("aaa")));
    verify(categoryRepository).save(updatedCategory);
  }
}
