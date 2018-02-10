package fr.knowledge.infra.handlers.favorites;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class SelectionRemovedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public SelectionRemovedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    elasticSearchService.deleteElement(ElasticIndexes.favorites.name(), event.getAggregateId());
  }
}
