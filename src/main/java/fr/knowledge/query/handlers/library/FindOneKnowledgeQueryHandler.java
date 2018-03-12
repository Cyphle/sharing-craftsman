package fr.knowledge.query.handlers.library;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.library.FindOneKnowledgeQuery;
import fr.knowledge.query.services.KnowledgeQueryService;

import java.util.List;

public class FindOneKnowledgeQueryHandler implements QueryHandler<CategoryElastic> {
  private KnowledgeQueryService knowledgeQueryService;

  public FindOneKnowledgeQueryHandler(KnowledgeQueryService knowledgeQueryService) {
    this.knowledgeQueryService = knowledgeQueryService;
  }

  @Override
  public List<CategoryElastic> handle(Query query) {
    return this.knowledgeQueryService.findOneById(((FindOneKnowledgeQuery) query).getId());
  }
}
