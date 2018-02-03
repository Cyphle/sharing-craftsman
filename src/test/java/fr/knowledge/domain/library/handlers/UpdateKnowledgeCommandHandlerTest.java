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

import java.util.HashMap;
import java.util.Map;
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
    Map<Id, Knowledge> knowledges = new HashMap<>();
    knowledges.put(Id.of("aaa"), Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is my content."));
    Category category = Category.of("aaa", "Architecture", knowledges);
    given(categoryRepository.get(Id.of("aaa"))).willReturn(Optional.of(category));

    updateKnowledgeCommandHandler = new UpdateKnowledgeCommandHandler(categoryRepository);
  }

  @Test
  public void should_update_knowledge() throws Exception {
    UpdateKnowledgeCommand command = new UpdateKnowledgeCommand("aaa", "aaa", "john@doe.fr", "Architecture hexagonale", "This is not my content.");

    updateKnowledgeCommandHandler.handle(command);

    Map<Id, Knowledge> updatedKnowledges = new HashMap<>();
    updatedKnowledges.put(Id.of("aaa"), Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is not my content."));
    Category updatedCategory = Category.of("aaa", "Architecture", updatedKnowledges);
    updatedCategory.saveChanges(new KnowledgeUpdatedEvent(Id.of("aaa"), Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is not my content.")));
    verify(categoryRepository).save(updatedCategory);
  }

  @Test(expected = KnowledgeNotFoundException.class)
  public void should_throw_exception_if_knowledge_does_not_exists() throws Exception {
    UpdateKnowledgeCommand command = new UpdateKnowledgeCommand("aaa", "bbb", "john@doe.fr", "Architecture hexagonale", "This is not my content.");

    updateKnowledgeCommandHandler.handle(command);
  }
}
