package fr.knowledge.infra.handlers.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryDeletedEvent;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDeletedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private CategoryDeletedEventHandler categoryDeletedEventHandler;

  @Before
  public void setUp() {
    categoryDeletedEventHandler = new CategoryDeletedEventHandler(elasticSearchService);
  }

  @Test
  public void should_delete_category() {
    CategoryDeletedEvent event = new CategoryDeletedEvent(Id.of("aaa"));

    categoryDeletedEventHandler.apply(event);

    verify(elasticSearchService).deleteElement(ElasticIndexes.library.name(), "aaa");
  }
}
