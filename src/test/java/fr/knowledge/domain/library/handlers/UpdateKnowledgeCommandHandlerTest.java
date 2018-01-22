package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.UpdateKnowledgeCommand;
import fr.knowledge.domain.library.events.KnowledgeUpdatedEvent;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateKnowledgeCommandHandlerTest {
  @Mock
  private CategoryRepository categoryRepository;
  private UpdateKnowledgeCommandHandler updateKnowledgeCommandHandler;

  @Before
  public void setUp() throws Exception {
    Category category = Category.of("aaa", "Architecture", Collections.singletonList(
            Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is my content.")
    ));
    given(categoryRepository.get(Id.of("aaa"))).willReturn(Optional.of(category));

    updateKnowledgeCommandHandler = new UpdateKnowledgeCommandHandler(categoryRepository);
  }

  @Test
  public void should_update_knowledge() throws Exception {
    UpdateKnowledgeCommand command = new UpdateKnowledgeCommand("aaa", "aaa", "john@doe.fr", "Architecture hexagonale", "This is not my content.");

    updateKnowledgeCommandHandler.handle(command);

    Knowledge updatedKnowledge = Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is not my content.");
    Category updatedCategory = Category.of("aaa", "Architecture", Collections.singletonList(updatedKnowledge));
    updatedCategory.saveChanges(new KnowledgeUpdatedEvent(Id.of("aaa"), updatedKnowledge));
    verify(categoryRepository).save(updatedCategory);
  }

  @Test
  public void should_throw_exception_if_knowledge_does_not_exists() throws Exception {
    UpdateKnowledgeCommand command = new UpdateKnowledgeCommand("aaa", "bbb", "john@doe.fr", "Architecture hexagonale", "This is not my content.");

    try {
      updateKnowledgeCommandHandler.handle(command);
      fail("Should have throw KnowledgeNotFoundException.");
    } catch (KnowledgeNotFoundException e) {
      assertThat(e).isNotNull();
    }
  }
}
