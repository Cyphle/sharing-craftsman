package fr.knowledge.infra.handlers.comments;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class CommentDeletedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public CommentDeletedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    elasticSearchService.deleteElement(ElasticIndexes.comments.name(), event.getAggregateId());
  }
}
