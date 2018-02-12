package fr.knowledge.infra.handlers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.KnowledgeUpdatedEvent;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import fr.knowledge.infra.mocks.KnowledgeUpdatedEventHandlerMock;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KnowledgeUpdatedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private KnowledgeUpdatedEventHandlerMock knowledgeUpdatedEventHandler;

  @Before
  public void setUp() {
    knowledgeUpdatedEventHandler = new KnowledgeUpdatedEventHandlerMock(elasticSearchService);
  }

  @Test
  public void should_update_knowledge() {
    KnowledgeUpdatedEvent event = new KnowledgeUpdatedEvent(Id.of("aaa"), Knowledge.of("kaa", "john@doe.fr", "My knowledge", "My content updated"));

    knowledgeUpdatedEventHandler.apply(event);

    verify(elasticSearchService).deleteElement(ElasticIndexes.library.name(), "aaa");
    CategoryElastic element = CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content updated")));
    verify(elasticSearchService).createElement(ElasticIndexes.library.name(), Mapper.fromObjectToJsonString(element));
  }
}
