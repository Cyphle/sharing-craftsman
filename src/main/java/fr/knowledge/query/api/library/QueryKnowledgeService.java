package fr.knowledge.query.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.library.FindOneCategoryQuery;
import fr.knowledge.query.queries.library.FindOneKnowledgeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryKnowledgeService {
  private final AuthorizationService authorizationService;
  private final QueryBus queryBus;

  @Autowired
  public QueryKnowledgeService(AuthorizationService authorizationService, QueryBus queryBus) {
    this.authorizationService = authorizationService;
    this.queryBus = queryBus;
  }

  public ResponseEntity getKnowledgeById(AuthorizationInfoDTO authorizationInfoDTO, String knowledgeId) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindOneKnowledgeQuery query = new FindOneKnowledgeQuery(knowledgeId);
    List knowledges = queryBus.send(query);
    if (knowledges.isEmpty())
      return new ResponseEntity<>("No knowledge for given id", HttpStatus.NO_CONTENT);
    return ResponseEntity.ok(knowledges.get(0));
  }
}
