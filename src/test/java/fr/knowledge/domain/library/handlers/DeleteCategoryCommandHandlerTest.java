package fr.knowledge.domain.library.handlers;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.commands.DeleteCategoryCommand;
import fr.knowledge.domain.library.ports.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCategoryCommandHandlerTest {
  @Mock
  private CategoryRepository categoryRepository;
  private DeleteCategoryCommandHandler deleteCategoryCommandHandler;

  @Before
  public void setUp() {
    given(categoryRepository.get(Id.of("aaa"))).willReturn(Optional.of(Category.of("aaa", "Architecture")));
    deleteCategoryCommandHandler = new DeleteCategoryCommandHandler(categoryRepository);
  }

  @Test
  public void should_delete_category() throws Exception {
    DeleteCategoryCommand command = new DeleteCategoryCommand("aaa");

    deleteCategoryCommandHandler.handle(command);

    Category category = Category.of("aaa", "Architecture");
    category.delete();
    verify(categoryRepository).save(category);
  }
}
