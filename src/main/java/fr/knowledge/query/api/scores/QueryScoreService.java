package fr.knowledge.query.api.scores;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.scores.FindScoresByMarkQuery;
import fr.knowledge.query.queries.scores.FindScoresForContentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QueryScoreService {
  private AuthorizationService authorizationService;
  private QueryBus queryBus;

  @Autowired
  public QueryScoreService(AuthorizationService authorizationService, QueryBus queryBus) {
    this.authorizationService = authorizationService;
    this.queryBus = queryBus;
  }

  public ResponseEntity getScoresByContentId(AuthorizationInfoDTO authorizationInfoDTO, String contentId) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindScoresForContentQuery query = new FindScoresForContentQuery(contentId);
    return ResponseEntity.ok(queryBus.send(query));
  }

  public ResponseEntity getScoresByMark(AuthorizationInfoDTO authorizationInfoDTO, int mark) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindScoresByMarkQuery query = new FindScoresByMarkQuery(mark);
    return ResponseEntity.ok(queryBus.send(query));
  }
}
