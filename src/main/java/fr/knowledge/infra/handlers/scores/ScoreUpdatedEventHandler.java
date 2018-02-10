package fr.knowledge.infra.handlers.scores;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.scores.events.ScoreUpdatedEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

import java.util.HashMap;
import java.util.Map;

public class ScoreUpdatedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public ScoreUpdatedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    Map<String, String> updates = new HashMap<>();
    updates.put("mark", "" + ((ScoreUpdatedEvent) event).getMark().value);
    elasticSearchService.updateElement(ElasticIndexes.scores.name(), event.getAggregateId(), updates);
  }
}
