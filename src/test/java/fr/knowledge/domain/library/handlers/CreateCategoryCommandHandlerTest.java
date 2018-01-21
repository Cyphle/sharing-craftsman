package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.events.CategoryCreatedEvent;
import fr.knowledge.domain.library.exceptions.AlreadyExistingCategoryException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Name;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCategoryCommandHandlerTest {
  private CreateCategoryCommandHandler createCategoryCommandHandler;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private CategoryRepository categoryRepository;

  @Before
  public void setUp() throws Exception {
    given(idGenerator.generate()).willReturn("aaa");

    createCategoryCommandHandler = new CreateCategoryCommandHandler(idGenerator, categoryRepository);
  }

  @Test
  public void should_add_category_when_receiving_command() throws Exception {
    CreateCategoryCommand command = new CreateCategoryCommand("Architecture");

    createCategoryCommandHandler.handle(command);

    verify(categoryRepository).getAll();
    Category category = Category.of("aaa", "Architecture");
    category.saveChanges(new CategoryCreatedEvent(Id.of("aaa"), Name.of("Architecture")));
    verify(categoryRepository).save(category);
  }

  @Test(expected = AlreadyExistingCategoryException.class)
  public void should_not_create_category_if_already_exists() throws Exception {
    given(categoryRepository.getAll()).willReturn(Collections.singletonList(Category.of("aaa", "Architecture")));
    CreateCategoryCommand command = new CreateCategoryCommand("Architecture");
    createCategoryCommandHandler.handle(command);
  }
}
