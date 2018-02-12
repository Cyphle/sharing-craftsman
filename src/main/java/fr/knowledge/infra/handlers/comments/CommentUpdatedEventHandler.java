package fr.knowledge.infra.handlers.comments;

import fr.knowledge.domain.comments.events.CommentUpdatedEvent;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

import java.util.HashMap;
import java.util.Map;

public class CommentUpdatedEventHandler implements EventHandler {
  private final ElasticSearchService elasticSearchService;

  public CommentUpdatedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    Map<String, String> updates = new HashMap<>();
    updates.put("content", ((CommentUpdatedEvent) event).getContentContent());
    elasticSearchService.updateElement(ElasticIndexes.comments.name(), event.getAggregateId(), updates);
  }
}
