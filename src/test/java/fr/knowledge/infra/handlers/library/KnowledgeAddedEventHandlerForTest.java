package fr.knowledge.infra.handlers.library;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class KnowledgeAddedEventHandlerForTest extends KnowledgeAddedEventHandler {
  public KnowledgeAddedEventHandlerForTest(ElasticSearchService elasticSearchService) {
    super(elasticSearchService);
  }

  @Override
  protected CategoryElastic getCategory(String aggregateId) {
    return CategoryElastic.of("aaa", "Architecture");
  }
}
