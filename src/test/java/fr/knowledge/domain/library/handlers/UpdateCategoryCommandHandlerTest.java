package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.aggregates.UpdateCategoryException;
import fr.knowledge.domain.library.commands.UpdateCategoryCommand;
import fr.knowledge.domain.library.events.CategoryUpdatedEvent;
import fr.knowledge.domain.library.exceptions.CategoryNotFoundException;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.library.valueobjects.Name;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCategoryCommandHandlerTest {
  @Mock
  private CategoryRepository categoryRepository;
  private UpdateCategoryCommandHandler updateCategoryCommandHandler;

  @Before
  public void setUp() throws Exception {
    given(categoryRepository.get(Id.of("aaa"))).willReturn(Optional.of(Category.of("aaa", "Architecture")));
    updateCategoryCommandHandler = new UpdateCategoryCommandHandler(categoryRepository);
  }

  @Test
  public void should_update_category() throws Exception {
    UpdateCategoryCommand command = new UpdateCategoryCommand("aaa", "SOLID");

    updateCategoryCommandHandler.handle(command);

    Category category = Category.of("aaa", "SOLID");
    category.saveChanges(new CategoryUpdatedEvent(Id.of("aaa"), Name.of("SOLID")));
    verify(categoryRepository).save(category);
  }

  @Test(expected = CategoryNotFoundException.class)
  public void should_not_update_category_if_category_is_not_found() throws Exception {
    given(categoryRepository.get(Id.of("bbb"))).willReturn(Optional.empty());
    UpdateCategoryCommand command = new UpdateCategoryCommand("bbb", "SOLID");

    updateCategoryCommandHandler.handle(command);
  }

  @Test
  public void should_not_update_category_if_new_name_is_empty() throws Exception {
    UpdateCategoryCommand command = new UpdateCategoryCommand("aaa", "");

    try {
      updateCategoryCommandHandler.handle(command);
      fail("Should have thrown UpdateCategoryException.");
    } catch (UpdateCategoryException e) {
      assertThat(e.getMessage()).isEqualTo("Name cannot be empty.");
    }
  }
}
