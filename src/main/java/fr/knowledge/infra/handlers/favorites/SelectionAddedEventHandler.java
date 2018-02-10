package fr.knowledge.infra.handlers.favorites;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.favorites.events.SelectionAddedEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.models.favorites.SelectionElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class SelectionAddedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public SelectionAddedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    SelectionElastic selectionElastic = SelectionElastic.of(event.getAggregateId(), ((SelectionAddedEvent) event).getUsernameContent(), ((SelectionAddedEvent) event).getContentType().name(), ((SelectionAddedEvent) event).getContentIdContent());
    String element = Mapper.fromObjectToJsonString(selectionElastic);
    elasticSearchService.createElement(ElasticIndexes.favorites.name(), element);
  }
}
