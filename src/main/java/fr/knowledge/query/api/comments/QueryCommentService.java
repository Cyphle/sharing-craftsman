package fr.knowledge.query.api.comments;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.comments.FindCommentsForContentQuery;
import fr.knowledge.query.queries.library.FindOneCategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryCommentService {
  private final AuthorizationService authorizationService;
  private final QueryBus queryBus;

  @Autowired
  public QueryCommentService(AuthorizationService authorizationService, QueryBus queryBus) {
    this.authorizationService = authorizationService;
    this.queryBus = queryBus;
  }

  public ResponseEntity getCommentsForContent(String contentId) {
    FindCommentsForContentQuery query = new FindCommentsForContentQuery(contentId);
    return ResponseEntity.ok(queryBus.send(query));
  }

  public ResponseEntity getCommentById(AuthorizationInfoDTO authorizationInfoDTO, String commentId) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindOneCategoryQuery query = new FindOneCategoryQuery(commentId);
    List comments = queryBus.send(query);
    if (comments.isEmpty())
      return new ResponseEntity<>("No comment for given id", HttpStatus.NO_CONTENT);
    return ResponseEntity.ok(comments.get(0));
  }
}
