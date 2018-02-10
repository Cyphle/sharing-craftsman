package fr.knowledge.query.bus;

import fr.knowledge.query.handlers.FindAllCategoriesQueryHandler;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.FindAllCategoriesQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleQueryBusTest {
  private QueryBus queryBus;

  @Before
  public void setUp() {
    queryBus = new SimpleQueryBus();
  }

  @Test
  public void should_register_find_all_categories_query_handler() {
    FindAllCategoriesQueryHandler findAllCategoriesQueryHandler = new FindAllCategoriesQueryHandler();
    queryBus.subscribe(FindAllCategoriesQuery.class, findAllCategoriesQueryHandler);

    Map<Class, QueryHandler> handlers = new HashMap<>();
    handlers.put(FindAllCategoriesQuery.class, findAllCategoriesQueryHandler);
    assertThat(queryBus.getHandlers()).isEqualTo(handlers);
  }
}
