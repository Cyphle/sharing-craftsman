package fr.knowledge.query.bus;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.query.handlers.library.FindAllCategoriesQueryHandler;
import fr.knowledge.query.handlers.library.FindOneCategoryQueryHandler;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.handlers.library.SearchCategoryQueryHandler;
import fr.knowledge.query.queries.library.FindAllCategoriesQuery;
import fr.knowledge.query.queries.library.FindOneCategoryQuery;
import fr.knowledge.query.queries.library.SearchCategoryQuery;
import fr.knowledge.query.services.CategoryQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class SimpleQueryBusTest {
  private QueryBus queryBus;
  @Mock
  private FindAllCategoriesQueryHandler findAllCategoriesQueryHandler;
  @Mock
  private FindOneCategoryQueryHandler findOneCategoryQueryHandler;
  @Mock
  private SearchCategoryQueryHandler searchCategoryQueryHandler;
  @Mock
  private CategoryQueryService categoryQueryService;

  @Before
  public void setUp() {
    given(findOneCategoryQueryHandler.handle(any(FindOneCategoryQuery.class))).willReturn(Collections.singletonList(CategoryElastic.of("aaa", "Architecture")));
    queryBus = new SimpleQueryBus();
  }

  @Test
  public void should_register_find_all_categories_query_handler() {
    FindAllCategoriesQueryHandler findAllCategoriesQueryHandler = new FindAllCategoriesQueryHandler(categoryQueryService);
    queryBus.subscribe(FindAllCategoriesQuery.class, findAllCategoriesQueryHandler);

    Map<Class, QueryHandler> handlers = new HashMap<>();
    handlers.put(FindAllCategoriesQuery.class, findAllCategoriesQueryHandler);
    assertThat(queryBus.getHandlers()).isEqualTo(handlers);
  }

  @Test
  public void should_dispatch_command_to_right_command_handler() {
    queryBus.subscribe(FindAllCategoriesQuery.class, findAllCategoriesQueryHandler);
    queryBus.subscribe(FindOneCategoryQuery.class, findOneCategoryQueryHandler);
    queryBus.subscribe(SearchCategoryQuery.class, searchCategoryQueryHandler);

    queryBus.send(new FindOneCategoryQuery("aaa"));

    FindOneCategoryQuery query = new FindOneCategoryQuery("aaa");
    assertThat(findOneCategoryQueryHandler.handle(query)).isEqualTo(Collections.singletonList(
            CategoryElastic.of("aaa", "Architecture")
    ));
  }
}
