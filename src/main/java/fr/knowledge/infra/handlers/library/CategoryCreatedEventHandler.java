package fr.knowledge.infra.handlers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.library.events.CategoryCreatedEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.models.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class CategoryCreatedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public CategoryCreatedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    CategoryElastic categoryElastic = new CategoryElastic(event.getAggregateId(), ((CategoryCreatedEvent) event).getNameContent());
    String element = Mapper.fromObjectToJsonString(categoryElastic);
    elasticSearchService.createElement(ElasticIndexes.library.name(), element);
    /*
      - Create category in elastic search
      - ElasticCategory (containing ElasticKnowledge)
     */
  }

  /*
  dispatch to query event handler (which will apply event to modify state)
   */
    /*
      - Dispatch to event handlers
      - Event handlers update elements in elasticsearch
     */
}
