package fr.knowledge.query.services;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryQueryService {
  private ElasticSearchService elasticSearchService;

  public CategoryQueryService(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  public List<CategoryElastic> findAllCategories() {
    SearchResult searchResult = elasticSearchService.findAllElements(ElasticIndexes.library.name());
    return searchResult.getHits(CategoryElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }
}
