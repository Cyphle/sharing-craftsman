package fr.knowledge.command.bus;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.handlers.CreateCategoryCommandHandler;
import fr.knowledge.domain.library.ports.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCommandBusTest {
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private CategoryRepository categoryRepository;
  private CommandBus commandBus;

  @Before
  public void setUp() throws Exception {
    commandBus = new SimpleCommandBus();
  }

  @Test
  public void should_register_create_category_command_handler() throws Exception {
    CreateCategoryCommandHandler createCategoryCommandHandler = new CreateCategoryCommandHandler(idGenerator, categoryRepository);
    commandBus.subscribe(CreateCategoryCommand.class, createCategoryCommandHandler);

    Map<Class, CommandHandler> handlers = new HashMap<>();
    handlers.put(CreateCategoryCommand.class, createCategoryCommandHandler);
    assertThat(commandBus.getHandlers()).isEqualTo(handlers);
  }
}
