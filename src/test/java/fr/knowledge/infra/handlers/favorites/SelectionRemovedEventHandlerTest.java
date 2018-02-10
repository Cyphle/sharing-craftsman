package fr.knowledge.infra.handlers.favorites;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SelectionRemovedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private SelectionRemovedEventHandler selectionRemovedEventHandler;

  @Before
  public void setUp() {
    selectionRemovedEventHandler = new SelectionRemovedEventHandler(elasticSearchService);
  }

  @Test
  public void should_delete_category() {
    SelectionRemovedEvent event = new SelectionRemovedEvent(Id.of("aaa"));

    selectionRemovedEventHandler.apply(event);

    verify(elasticSearchService).deleteElement(ElasticIndexes.favorites.name(), "aaa");
  }
}
