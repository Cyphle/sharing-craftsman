package fr.knowledge.query.services;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import fr.knowledge.query.queries.SearchCriteria;
import io.searchbox.core.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  public List<CategoryElastic> findOneById(String id) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.library.name(), "id", id);
    return searchResult.getHits(CategoryElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }

  public List<CategoryElastic> search(SearchCriteria criteria) {
    Map<String, String> searchCriteria = new HashMap<>();
    criteria.getCriteria()
            .forEach((key, value) -> searchCriteria.put(ElasticIndexes.library.name().toUpperCase() + "." + key.searchKey, value));
    SearchResult searchResult = elasticSearchService.criteriaSearchElements(ElasticIndexes.library.name(), searchCriteria);
    return searchResult.getHits(CategoryElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }
}
