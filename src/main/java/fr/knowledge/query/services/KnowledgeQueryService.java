package fr.knowledge.query.services;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeQueryService {
  private final ElasticSearchService elasticSearchService;

  public KnowledgeQueryService(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  public List<CategoryElastic> findOneById(String knowledgeId) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.library.name(), "knowledges.id", knowledgeId);
    return searchResult.getHits(CategoryElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }
}
