package fr.knowledge.infra.handlers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.library.events.KnowledgeDeletedEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.SearchResult;

public class KnowledgeDeletedEventHandler implements EventHandler {
  private final ElasticSearchService elasticSearchService;

  public KnowledgeDeletedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    CategoryElastic category = getCategory(event.getAggregateId());
    category.deleteKnowledge(((KnowledgeDeletedEvent) event).getKnowledgeId().getId());
    elasticSearchService.deleteElement(ElasticIndexes.library.name(), event.getAggregateId());
    elasticSearchService.createElement(ElasticIndexes.library.name(), Mapper.fromObjectToJsonString(category));
  }

  protected CategoryElastic getCategory(String aggregateId) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.library.name(), "id", aggregateId);

    return searchResult.getHits(CategoryElastic.class).stream()
            .map(h -> h.source)
            .findAny()
            .orElseThrow(RuntimeException::new);
  }
}
