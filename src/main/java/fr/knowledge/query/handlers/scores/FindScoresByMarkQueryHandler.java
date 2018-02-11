package fr.knowledge.query.handlers.scores;

import fr.knowledge.infra.models.scores.ScoreElastic;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.scores.FindScoresByMarkQuery;
import fr.knowledge.query.services.ScoreQueryService;

import java.util.List;

public class FindScoresByMarkQueryHandler implements QueryHandler<ScoreElastic> {
  private ScoreQueryService scoreQueryService;

  public FindScoresByMarkQueryHandler(ScoreQueryService scoreQueryService) {
    this.scoreQueryService = scoreQueryService;
  }

  @Override
  public List<ScoreElastic> handle(Query query) {
    return scoreQueryService.findScoresWithMarkOf(((FindScoresByMarkQuery) query).getMark());
  }
}
