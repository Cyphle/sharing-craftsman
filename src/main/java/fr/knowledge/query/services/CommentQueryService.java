package fr.knowledge.query.services;

import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class CommentQueryService {
  private ElasticSearchService elasticSearchService;

  public CommentQueryService(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  public List<CommentElastic> findOneById(String id) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.comments.name(), "id", id);
    return searchResult.getHits(CommentElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }

  public List<CommentElastic> findCommentsFor(String contentId) {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.comments.name(), "contentId", contentId);
    return searchResult.getHits(CommentElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());
  }
}
