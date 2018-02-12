package fr.knowledge.query.services;

import fr.knowledge.infra.models.scores.ScoreElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreQueryService {
  private ElasticSearchService elasticSearchService;

  public ScoreQueryService(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  public List<ScoreElastic> findScoresFor(String contentId) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.scores.name(), "contentId", contentId);
    return searchResult.getHits(ScoreElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }

  public List<ScoreElastic> findScoresWithMarkOf(int mark) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.scores.name(), "mark", "" + mark);
    return searchResult.getHits(ScoreElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }
}
