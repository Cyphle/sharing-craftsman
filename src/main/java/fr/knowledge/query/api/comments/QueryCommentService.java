package fr.knowledge.query.api.comments;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.comments.FindCommentsForContentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QueryCommentService {
  private final AuthorizationService authorizationService;
  private final QueryBus queryBus;

  @Autowired
  public QueryCommentService(AuthorizationService authorizationService, QueryBus queryBus) {
    this.authorizationService = authorizationService;
    this.queryBus = queryBus;
  }

  public ResponseEntity getCommentsForContent(AuthorizationInfoDTO authorizationInfoDTO, String contentId) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindCommentsForContentQuery query = new FindCommentsForContentQuery(contentId);
    return ResponseEntity.ok(queryBus.send(query));
  }
}
