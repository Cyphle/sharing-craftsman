package fr.knowledge.infra.handlers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.KnowledgeDeletedEvent;
import fr.knowledge.infra.mocks.KnowledgeDeletedEventHandlerMock;
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
public class KnowledgeDeletedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private KnowledgeDeletedEventHandlerMock knowledgeDeletedEventHandler;

  @Before
  public void setUp() {
    knowledgeDeletedEventHandler = new KnowledgeDeletedEventHandlerMock(elasticSearchService);
  }

  @Test
  public void should_deleted_knowledge() {
    KnowledgeDeletedEvent event = new KnowledgeDeletedEvent(Id.of("aaa"), Id.of("kaa"));

    knowledgeDeletedEventHandler.apply(event);

    verify(elasticSearchService).deleteElement(ElasticIndexes.library.name(), "aaa");
    CategoryElastic element = CategoryElastic.of("aaa", "Architecture");
    verify(elasticSearchService).createElement(ElasticIndexes.library.name(), Mapper.fromObjectToJsonString(element));
  }
}
