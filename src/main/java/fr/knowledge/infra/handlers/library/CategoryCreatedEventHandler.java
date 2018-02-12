package fr.knowledge.infra.handlers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.library.events.CategoryCreatedEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class CategoryCreatedEventHandler implements EventHandler {
  private final ElasticSearchService elasticSearchService;

  public CategoryCreatedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    CategoryElastic categoryElastic = CategoryElastic.of(event.getAggregateId(), ((CategoryCreatedEvent) event).getNameContent());
    String element = Mapper.fromObjectToJsonString(categoryElastic);
    elasticSearchService.createElement(ElasticIndexes.library.name(), element);
  }
}
