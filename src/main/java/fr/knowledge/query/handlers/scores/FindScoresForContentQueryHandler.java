package fr.knowledge.query.handlers.scores;

import fr.knowledge.infra.models.scores.ScoreElastic;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.scores.FindScoresForContentQuery;
import fr.knowledge.query.services.ScoreQueryService;

import java.util.List;

public class FindScoresForContentQueryHandler implements QueryHandler<ScoreElastic> {
  private ScoreQueryService scoreQueryService;

  public FindScoresForContentQueryHandler(ScoreQueryService scoreQueryService) {
    this.scoreQueryService = scoreQueryService;
  }

  @Override
  public List<ScoreElastic> handle(Query query) {
    return scoreQueryService.findScoresFor(((FindScoresForContentQuery) query).getContentId());
  }
}
