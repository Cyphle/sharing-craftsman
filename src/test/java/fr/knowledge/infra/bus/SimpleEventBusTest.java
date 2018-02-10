package fr.knowledge.infra.bus;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryCreatedEvent;
import fr.knowledge.domain.library.events.CategoryDeletedEvent;
import fr.knowledge.domain.library.events.CategoryUpdatedEvent;
import fr.knowledge.domain.library.valueobjects.Name;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.handlers.library.CategoryCreatedEventHandler;
import fr.knowledge.infra.handlers.library.CategoryDeletedEventHandler;
import fr.knowledge.infra.handlers.library.CategoryUpdatedEventHandler;
import fr.knowledge.infra.repositories.ElasticSearchService;
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
public class SimpleEventBusTest {
  private EventBus eventBus;
  @Mock
  private CategoryCreatedEventHandler categoryCreatedEventHandler;
  @Mock
  private CategoryUpdatedEventHandler categoryUpdatedEventHandler;
  @Mock
  private CategoryDeletedEventHandler categoryDeletedEventHandler;
  @Mock
  private ElasticSearchService elasticSearchService;

  @Before
  public void setUp() {
    eventBus = new SimpleEventBus();
  }

  @Test
  public void should_register_category_created_event_handler() {
    CategoryCreatedEventHandler categoryCreatedEventHandler = new CategoryCreatedEventHandler(elasticSearchService);
    eventBus.subscribe(CategoryCreatedEvent.class, categoryCreatedEventHandler);

    Map<Class, EventHandler> handlers = new HashMap<>();
    handlers.put(CategoryCreatedEvent.class, categoryCreatedEventHandler);
    assertThat(eventBus.getHandlers()).isEqualTo(handlers);
  }

  @Test
  public void should_dispatch_command_to_right_command_handler() {
    eventBus.subscribe(CategoryCreatedEvent.class, categoryCreatedEventHandler);
    eventBus.subscribe(CategoryUpdatedEvent.class, categoryUpdatedEventHandler);
    eventBus.subscribe(CategoryDeletedEvent.class, categoryDeletedEventHandler);

    eventBus.apply(new CategoryCreatedEvent(Id.of("aaa"), Name.of("Architecture")));

    CategoryCreatedEvent event = new CategoryCreatedEvent(Id.of("aaa"), Name.of("Architecture"));
    verify(categoryCreatedEventHandler).apply(event);
  }
}
