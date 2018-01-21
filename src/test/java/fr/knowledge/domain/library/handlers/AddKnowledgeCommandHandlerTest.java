package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.AddKnowledgeCommand;
import fr.knowledge.domain.library.events.AddedKnowledgeEvent;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddKnowledgeCommandHandlerTest {
  private Category category;
  private AddKnowledgeCommandHandler addKnowledgeCommandHandler;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private CategoryRepository categoryRepository;

  @Before
  public void setUp() throws Exception {
    given(idGenerator.generate()).willReturn("aaa");
    category = Category.of("aaa", "Architecture");
    given(categoryRepository.get(Id.of("aaa"))).willReturn(Optional.of(category));
    addKnowledgeCommandHandler = new AddKnowledgeCommandHandler(idGenerator, categoryRepository);
  }

  @Test
  public void should_add_knowledge_to_category() throws Exception {
    AddKnowledgeCommand command = new AddKnowledgeCommand("aaa", "john@doe.fr", "Architecture hexagonale", "This is my content");

    addKnowledgeCommandHandler.handle(command);

    Knowledge knowledge = Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is my content");
    Category updatedCategory = Category.of("aaa", "Architecture");
    updatedCategory.apply(new AddedKnowledgeEvent(Id.of("aaa"), knowledge));
    verify(categoryRepository).save(updatedCategory);
  }
}
