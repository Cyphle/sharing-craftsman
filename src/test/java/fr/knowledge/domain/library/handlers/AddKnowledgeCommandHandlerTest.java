package fr.knowledge.domain.library.handlers;

import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.AddKnowledgeCommand;
import fr.knowledge.domain.library.events.KnowledgeAddedEvent;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddKnowledgeCommandHandlerTest {
  private AddKnowledgeCommandHandler addKnowledgeCommandHandler;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private CategoryRepository categoryRepository;

  @Before
  public void setUp() throws Exception {
    given(idGenerator.generate()).willReturn("aaa");
    given(categoryRepository.get(Id.of("aaa"))).willReturn(Optional.of(Category.of("aaa", "Architecture")));
    addKnowledgeCommandHandler = new AddKnowledgeCommandHandler(idGenerator, categoryRepository);
  }

  @Test
  public void should_add_knowledge_to_category() throws Exception {
    AddKnowledgeCommand command = new AddKnowledgeCommand("aaa", "john@doe.fr", "Architecture hexagonale", "This is my content");

    addKnowledgeCommandHandler.handle(command);

    Knowledge knowledge = Knowledge.of("aaa", "john@doe.fr", "Architecture hexagonale", "This is my content");
    Category updatedCategory = Category.of("aaa", "Architecture");
    updatedCategory.addKnowledge(knowledge);
    verify(categoryRepository).save(updatedCategory);
  }

  @Test
  public void should_throw_category_not_found_exception_if_category_is_not_found() throws Exception {
    given(categoryRepository.get(Id.of("bbb"))).willReturn(Optional.empty());
    AddKnowledgeCommand command = new AddKnowledgeCommand("bbb", "", "Architecture hexagonale", "This is my content");

    try {
      addKnowledgeCommandHandler.handle(command);
      fail("Should have thrown AddKnowledgeException");
    } catch (CategoryNotFoundException e) {
      assertThat(e).isNotNull();
    }
  }

  @Test
  public void should_not_add_knowledge_if_creator_is_missing() throws Exception {
    AddKnowledgeCommand command = new AddKnowledgeCommand("aaa", "", "Architecture hexagonale", "This is my content");

    try {
      addKnowledgeCommandHandler.handle(command);
      fail("Should have thrown AddKnowledgeException");
    } catch (AddKnowledgeException e) {
      assertThat(e.getMessage()).isEqualTo("Creator cannot be empty.");
    }
  }

  @Test
  public void should_not_add_knowledge_if_title_is_empty() throws Exception {
    AddKnowledgeCommand command = new AddKnowledgeCommand("aaa", "john@doe.fr", "", "This is my content");

    try {
      addKnowledgeCommandHandler.handle(command);
      fail("Should have thrown AddKnowledgeException");
    } catch (AddKnowledgeException e) {
      assertThat(e.getMessage()).isEqualTo("Title cannot be empty.");
    }
  }

  @Test
  public void should_not_add_knowledge_if_content_is_empty() throws Exception {
    AddKnowledgeCommand command = new AddKnowledgeCommand("aaa", "john@doe.fr", "Architecture hexagonale", "");

    try {
      addKnowledgeCommandHandler.handle(command);
      fail("Should have thrown AddKnowledgeException");
    } catch (AddKnowledgeException e) {
      assertThat(e.getMessage()).isEqualTo("Content cannot be empty.");
    }
  }
}
