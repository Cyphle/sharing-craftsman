package fr.knowledge.query.handlers.comments;

import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.comments.FindOneCommentQuery;
import fr.knowledge.query.services.CommentQueryService;

import java.util.List;

public class FindOneCommentQueryHandler implements QueryHandler<CommentElastic> {
  private CommentQueryService commentQueryService;

  public FindOneCommentQueryHandler(CommentQueryService commentQueryService) {
    this.commentQueryService = commentQueryService;
  }

  @Override
  public List<CommentElastic> handle(Query query) {
    return commentQueryService.findOneById(((FindOneCommentQuery) query).getId());
  }
}
