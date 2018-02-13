package fr.knowledge.query.api.favorites;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.infra.models.favorites.SelectionElastic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@Api(description = "Endpoints to get favorites")
public class QuerySelectionController {
  private final QuerySelectionService querySelectionService;

  @Autowired
  public QuerySelectionController(QuerySelectionService querySelectionService) {
    this.querySelectionService = querySelectionService;
  }

  @ApiOperation(value = "Endpoint to get favorites for a given user", response = SelectionElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.POST, value = "/search")
  public ResponseEntity getSelectionsOfUser(@RequestHeader("client") String client,
                                              @RequestHeader("secret") String secret,
                                              @RequestHeader("username") String username,
                                              @RequestHeader("access-token") String accessToken,
                                              @RequestBody UsernameDTO user) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return querySelectionService.getSelectionsOfUser(authorizationInfoDTO, user.getUsername());
  }
}
