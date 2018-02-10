package fr.knowledge.infra.handlers.scores;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.events.ScoreDeletedEvent;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScoreDeletedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private ScoreDeletedEventHandler scoreDeletedEventHandler;

  @Before
  public void setUp() {
    scoreDeletedEventHandler = new ScoreDeletedEventHandler(elasticSearchService);
  }

  @Test
  public void should_delete_category() {
    ScoreDeletedEvent event = new ScoreDeletedEvent(Id.of("saa"));

    scoreDeletedEventHandler.apply(event);

    verify(elasticSearchService).deleteElement(ElasticIndexes.scores.name(), "saa");
  }
}
