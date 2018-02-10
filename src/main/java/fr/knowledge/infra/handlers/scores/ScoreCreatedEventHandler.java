package fr.knowledge.infra.handlers.scores;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.models.scores.ScoreElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class ScoreCreatedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public ScoreCreatedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    ScoreElastic scoreElastic = ScoreElastic.of(event.getAggregateId(), ((ScoreCreatedEvent) event).getGiverContent(), ((ScoreCreatedEvent) event).getContentType().name(), ((ScoreCreatedEvent) event).getContentIdContent(), ((ScoreCreatedEvent) event).getMark().value);
    String element = Mapper.fromObjectToJsonString(scoreElastic);
    elasticSearchService.createElement(ElasticIndexes.scores.name(), element);
  }
}
