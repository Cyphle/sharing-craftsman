package fr.knowledge.query.handlers;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.query.queries.FindOneCategoryQuery;
import fr.knowledge.query.services.CategoryQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FindOneCategoryQueryHandlerTest {
  private FindOneCategoryQueryHandler findOneCategoryQueryHandler;
  @Mock
  private CategoryQueryService categoryQueryService;

  @Before
  public void setUp() {
    given(categoryQueryService.findOneById("aaa")).willReturn(Arrays.asList(CategoryElastic.of("aaa", "Architecture")));
    findOneCategoryQueryHandler = new FindOneCategoryQueryHandler(categoryQueryService);
  }

  @Test
  public void should_find_one_category_by_id() {
    FindOneCategoryQuery query = new FindOneCategoryQuery("aaa");

    List<CategoryElastic> categories = findOneCategoryQueryHandler.handle(query);
    assertThat(categories).containsExactly(CategoryElastic.of("aaa", "Architecture"));
  }
}
