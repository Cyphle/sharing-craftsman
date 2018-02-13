package fr.knowledge.query.services;

import fr.knowledge.infra.models.favorites.SelectionElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class SelectionQueryService {
  private final ElasticSearchService elasticSearchService;

  public SelectionQueryService(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  public List<SelectionElastic> findSelectionForUser(String username) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.favorites.name(), "username", username);
    return searchResult.getHits(SelectionElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }
}
