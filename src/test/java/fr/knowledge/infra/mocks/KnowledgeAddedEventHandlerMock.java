package fr.knowledge.infra.mocks;

import fr.knowledge.infra.handlers.library.KnowledgeAddedEventHandler;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class KnowledgeAddedEventHandlerMock extends KnowledgeAddedEventHandler {
  public KnowledgeAddedEventHandlerMock(ElasticSearchService elasticSearchService) {
    super(elasticSearchService);
  }

  @Override
  protected CategoryElastic getCategory(String aggregateId) {
    return CategoryElastic.of("aaa", "Architecture");
  }
}
