package fr.knowledge.infra.handlers.scores;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.infra.models.scores.ScoreElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScoreCreatedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private ScoreCreatedEventHandler scoreCreatedEventHandler;

  @Before
  public void setUp() {
    scoreCreatedEventHandler = new ScoreCreatedEventHandler(elasticSearchService);
  }

  @Test
  public void should_create_category_in_elastic_search() {
    ScoreCreatedEvent event = new ScoreCreatedEvent(Id.of("saa"), Username.from("john@doe.fr"), ContentType.KNOWLEDGE, Id.of("kaa"), Mark.FOUR);

    scoreCreatedEventHandler.apply(event);

    ScoreElastic score = ScoreElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "kaa", Mark.FOUR.value);
    verify(elasticSearchService).createElement(ElasticIndexes.scores.name(), Mapper.fromObjectToJsonString(score));
  }
}
