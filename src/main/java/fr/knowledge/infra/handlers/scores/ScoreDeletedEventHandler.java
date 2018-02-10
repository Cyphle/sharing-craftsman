package fr.knowledge.infra.handlers.scores;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class ScoreDeletedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public ScoreDeletedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    elasticSearchService.deleteElement(ElasticIndexes.scores.name(), event.getAggregateId());
  }
}
