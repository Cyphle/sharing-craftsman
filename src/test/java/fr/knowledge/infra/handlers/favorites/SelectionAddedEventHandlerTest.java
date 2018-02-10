package fr.knowledge.infra.handlers.favorites;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.events.SelectionAddedEvent;
import fr.knowledge.infra.models.favorites.SelectionElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SelectionAddedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private SelectionAddedEventHandler selectionAddedEventHandler;

  @Before
  public void setUp() {
    selectionAddedEventHandler = new SelectionAddedEventHandler(elasticSearchService);
  }

  @Test
  public void should_create_category_in_elastic_search() {
    SelectionAddedEvent event = new SelectionAddedEvent(Id.of("caa"), Username.from("john@doe.fr"), ContentType.KNOWLEDGE, Id.of("kaa"));

    selectionAddedEventHandler.apply(event);

    SelectionElastic selection = SelectionElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "kaa");

    verify(elasticSearchService).createElement(ElasticIndexes.favorites.name(), Mapper.fromObjectToJsonString(selection));
  }
}
