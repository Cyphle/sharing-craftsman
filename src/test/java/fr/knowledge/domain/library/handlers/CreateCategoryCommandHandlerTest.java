package fr.knowledge.domain.library.handlers;

import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.exceptions.AlreadyExistingCategoryException;
import fr.knowledge.domain.library.exceptions.CategoryException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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
  public void setUp() {
    given(idGenerator.generate()).willReturn("aaa");

    createCategoryCommandHandler = new CreateCategoryCommandHandler(idGenerator, categoryRepository);
  }

  @Test
  public void should_add_category_when_receiving_command() throws Exception {
    CreateCategoryCommand command = new CreateCategoryCommand("Architecture");

    createCategoryCommandHandler.handle(command);

    verify(categoryRepository).getAll();
    Category category = Category.newCategory("aaa", "Architecture");
    verify(categoryRepository).save(category);
  }

  @Test
  public void should_not_add_category_if_name_is_empty() throws Exception {
    CreateCategoryCommand command = new CreateCategoryCommand("");

    try {
      createCategoryCommandHandler.handle(command);
      fail("Should have thrown CategoryException");
    } catch (CategoryException e) {
      assertThat(e.getMessage()).isEqualTo("Name cannot be empty.");
    }
  }

  @Test(expected = AlreadyExistingCategoryException.class)
  public void should_not_create_category_if_already_exists() throws Exception {
    given(categoryRepository.getAll()).willReturn(Collections.singletonList(Category.of("aaa", "Architecture")));
    CreateCategoryCommand command = new CreateCategoryCommand("Architecture");
    createCategoryCommandHandler.handle(command);
  }
}
