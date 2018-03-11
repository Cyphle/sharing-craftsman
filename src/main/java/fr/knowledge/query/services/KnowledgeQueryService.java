package fr.knowledge.query.services;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.SearchResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeQueryService {
  private final ElasticSearchService elasticSearchService;

  public KnowledgeQueryService(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  public List<KnowledgeElastic> findOneById(String knowledgeId) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.library.name(), "knowledges.id", knowledgeId);
    List<CategoryElastic> categories = searchResult.getHits(CategoryElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
    return Collections.singletonList(
            categories
                    .get(0)
                    .getKnowledges()
                    .stream()
                    .filter(knowledge -> knowledge.getId().equals(knowledgeId))
                    .findAny()
                    .orElse(KnowledgeElastic.of("", "", "", ""))
    );
  }
}
