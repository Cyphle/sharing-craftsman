package fr.knowledge.infra.handlers.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.library.events.CategoryUpdatedEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

import java.util.HashMap;
import java.util.Map;

public class CategoryUpdatedEventHandler implements EventHandler {
  private final ElasticSearchService elasticSearchService;

  public CategoryUpdatedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    Map<String, String> updates = new HashMap<>();
    updates.put("name", ((CategoryUpdatedEvent) event).getNewNameContent());
    elasticSearchService.updateElement(ElasticIndexes.library.name(), event.getAggregateId(), updates);
  }
}
