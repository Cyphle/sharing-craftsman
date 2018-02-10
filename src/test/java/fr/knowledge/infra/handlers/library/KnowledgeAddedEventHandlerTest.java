package fr.knowledge.infra.handlers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.KnowledgeAddedEvent;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import fr.knowledge.infra.mocks.KnowledgeAddedEventHandlerMock;
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
public class KnowledgeAddedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private KnowledgeAddedEventHandlerMock knowledgeAddedEventHandler;

  @Before
  public void setUp() {
    knowledgeAddedEventHandler = new KnowledgeAddedEventHandlerMock(elasticSearchService);
  }

  @Test
  public void should_add_knowledge_to_category() {
    KnowledgeAddedEvent event = new KnowledgeAddedEvent(Id.of("aaa"), Knowledge.of("kaa", "john@doe.fr", "My knowledge", "My content"));

    knowledgeAddedEventHandler.apply(event);

    verify(elasticSearchService).deleteElement(ElasticIndexes.library.name(), "aaa");
    CategoryElastic element = CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")));
    verify(elasticSearchService).createElement(ElasticIndexes.library.name(), Mapper.fromObjectToJsonString(element));
  }
}
