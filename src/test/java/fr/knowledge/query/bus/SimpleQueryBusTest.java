package fr.knowledge.query.bus;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.commands.QueryAllCategoriesCommand;
import fr.knowledge.domain.library.commands.QueryOneCategoryCommand;
import fr.knowledge.domain.library.commands.SearchCategoryCommand;
import fr.knowledge.domain.library.handlers.QueryAllCategoriesCommandHandler;
import fr.knowledge.domain.library.handlers.QueryOneCategoryCommandHandler;
import fr.knowledge.domain.library.handlers.SearchCategoryCommandHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleQueryBusTest {
  private QueryBus queryBus;
  @Mock
  private QueryAllCategoriesCommandHandler queryAllCategoriesCommandHandler;
  @Mock
  private QueryOneCategoryCommandHandler queryOneCategoryCommandHandler;
  @Mock
  private SearchCategoryCommandHandler searchCategoryCommandHandler;

  @Before
  public void setUp() {
    queryBus = new SimpleQueryBus();
  }

  @Test
  public void should_register_query_all_categories_command_handler() {
    QueryAllCategoriesCommandHandler queryAllCategoriesCommandHandler = new QueryAllCategoriesCommandHandler();
    queryBus.subscribe(QueryAllCategoriesCommand.class, queryAllCategoriesCommandHandler);

    Map<Class, CommandHandler> handlers = new HashMap<>();
    handlers.put(QueryAllCategoriesCommand.class, queryAllCategoriesCommandHandler);
    assertThat(queryBus.getHandlers()).isEqualTo(handlers);
  }

  @Test
  public void should_dispatch_command_to_right_command_handler() throws Exception {
    queryBus.subscribe(QueryAllCategoriesCommand.class, queryAllCategoriesCommandHandler);
    queryBus.subscribe(QueryOneCategoryCommand.class, queryOneCategoryCommandHandler);
    queryBus.subscribe(SearchCategoryCommand.class, searchCategoryCommandHandler);

    queryBus.send(new QueryOneCategoryCommand("aaa"));

    QueryOneCategoryCommand command = new QueryOneCategoryCommand("aaa");
    verify(queryOneCategoryCommandHandler).handle(command);
  }
}
