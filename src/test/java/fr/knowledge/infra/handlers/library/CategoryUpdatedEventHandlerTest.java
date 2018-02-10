package fr.knowledge.infra.handlers.library;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryUpdatedEvent;
import fr.knowledge.domain.library.valueobjects.Name;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryUpdatedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private CategoryUpdatedEventHandler categoryUpdatedEventHandler;

  @Before
  public void setUp() {
    categoryUpdatedEventHandler = new CategoryUpdatedEventHandler(elasticSearchService);
  }

  @Test
  public void should_create_category_in_elastic_search() {
    CategoryUpdatedEvent event = new CategoryUpdatedEvent(Id.of("aaa"), Name.of("SOLID"));

    categoryUpdatedEventHandler.apply(event);

    Map<String, String> updates = new HashMap<>();
    updates.put("name", "SOLID");
    verify(elasticSearchService).updateElement(ElasticIndexes.library.name(), "aaa", updates);
  }
}
