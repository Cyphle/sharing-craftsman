package fr.knowledge.query.handlers.library;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.query.handlers.library.FindAllCategoriesQueryHandler;
import fr.knowledge.query.queries.library.FindAllCategoriesQuery;
import fr.knowledge.query.services.CategoryQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FindAllCategoriesQueryHandlerTest {
  private FindAllCategoriesQueryHandler findAllCategoriesQueryHandler;
  @Mock
  private CategoryQueryService categoryQueryService;

  @Before
  public void setUp() {
    given(categoryQueryService.findAllCategories()).willReturn(Collections.singletonList(CategoryElastic.of("aaa", "Architecture")));
    findAllCategoriesQueryHandler = new FindAllCategoriesQueryHandler(categoryQueryService);
  }

  @Test
  public void should_find_all_categories() {
    FindAllCategoriesQuery query = new FindAllCategoriesQuery();

    List<CategoryElastic> categories = findAllCategoriesQueryHandler.handle(query);

    assertThat(categories).containsExactly(CategoryElastic.of("aaa", "Architecture"));
  }
}
