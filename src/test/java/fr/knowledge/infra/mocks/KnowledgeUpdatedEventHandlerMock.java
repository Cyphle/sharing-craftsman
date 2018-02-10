package fr.knowledge.infra.mocks;

import fr.knowledge.infra.handlers.library.KnowledgeUpdatedEventHandler;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import fr.knowledge.infra.repositories.ElasticSearchService;

import java.util.Collections;

public class KnowledgeUpdatedEventHandlerMock extends KnowledgeUpdatedEventHandler {
  public KnowledgeUpdatedEventHandlerMock(ElasticSearchService elasticSearchService) {
    super(elasticSearchService);
  }

  @Override
  protected CategoryElastic getCategory(String aggregateId) {
    return CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "Content")));
  }
}
