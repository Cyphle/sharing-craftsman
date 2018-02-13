package fr.knowledge.query.api.favorites;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.favorites.FindSelectionForUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuerySelectionService {
  private final AuthorizationService authorizationService;
  private final QueryBus queryBus;

  @Autowired
  public QuerySelectionService(AuthorizationService authorizationService, QueryBus queryBus) {
    this.authorizationService = authorizationService;
    this.queryBus = queryBus;
  }

  public ResponseEntity getSelectionsOfUser(AuthorizationInfoDTO authorizationInfoDTO, String username) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO) || !authorizationService.areUsernameEquals(authorizationInfoDTO.getUsername(), username))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindSelectionForUserQuery query = new FindSelectionForUserQuery(username);
    return ResponseEntity.ok(queryBus.send(query));
  }
}
