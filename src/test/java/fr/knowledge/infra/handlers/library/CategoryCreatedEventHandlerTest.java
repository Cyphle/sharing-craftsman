package fr.knowledge.infra.handlers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryCreatedEvent;
import fr.knowledge.domain.library.valueobjects.Name;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryCreatedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private CategoryCreatedEventHandler categoryCreatedEventHandler;

  @Before
  public void setUp() {
    categoryCreatedEventHandler = new CategoryCreatedEventHandler(elasticSearchService);
  }

  @Test
  public void should_create_category_in_elastic_search() {
    CategoryCreatedEvent event = new CategoryCreatedEvent(Id.of("aaa"), Name.of("Architecture"));
    categoryCreatedEventHandler.apply(event);

    CategoryElastic category = new CategoryElastic("aaa", "Architecture");
    verify(elasticSearchService).createElement(ElasticIndexes.library.name(), Mapper.fromObjectToJsonString(category));
  }
}
