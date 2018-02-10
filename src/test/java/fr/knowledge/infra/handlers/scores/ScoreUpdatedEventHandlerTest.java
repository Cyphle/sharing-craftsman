package fr.knowledge.infra.handlers.scores;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.events.ScoreUpdatedEvent;
import fr.knowledge.domain.scores.valueobjects.Mark;
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
public class ScoreUpdatedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private ScoreUpdatedEventHandler scoreUpdatedEventHandler;

  @Before
  public void setUp() {
    scoreUpdatedEventHandler = new ScoreUpdatedEventHandler(elasticSearchService);
  }

  @Test
  public void should_create_category_in_elastic_search() {
    ScoreUpdatedEvent event = new ScoreUpdatedEvent(Id.of("saa"), Mark.THREE);

    scoreUpdatedEventHandler.apply(event);

    Map<String, String> updates = new HashMap<>();
    updates.put("mark", "3");
    verify(elasticSearchService).updateElement(ElasticIndexes.scores.name(), "saa", updates);
  }
}
