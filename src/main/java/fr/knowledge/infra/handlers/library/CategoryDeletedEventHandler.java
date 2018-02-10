package fr.knowledge.infra.handlers.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class CategoryDeletedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public CategoryDeletedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    elasticSearchService.deleteElement(ElasticIndexes.library.name(), event.getAggregateId());
  }
}
