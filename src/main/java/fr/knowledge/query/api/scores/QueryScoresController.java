package fr.knowledge.query.api.scores;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.infra.models.favorites.SelectionElastic;
import fr.knowledge.query.api.favorites.UsernameDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scores")
@Api(description = "Endpoints to get favorites")
public class QueryScoresController {
  private QueryScoreService queryScoreService;

  @Autowired
  public QueryScoresController(QueryScoreService queryScoreService) {
    this.queryScoreService = queryScoreService;
  }

  @ApiOperation(value = "Endpoint to get scores for a given content id", response = SelectionElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET, value = "/contentId/{contentId}")
  public ResponseEntity getScoresByContentId(@RequestHeader("client") String client,
                                            @RequestHeader("secret") String secret,
                                            @RequestHeader("username") String username,
                                            @RequestHeader("access-token") String accessToken,
                                             @PathVariable String contentId) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryScoreService.getScoresByContentId(authorizationInfoDTO, contentId);
  }

  @ApiOperation(value = "Endpoint to get scores for a given mark", response = SelectionElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET, value = "/mark/{mark}")
  public ResponseEntity getScoresByContentId(@RequestHeader("client") String client,
                                            @RequestHeader("secret") String secret,
                                            @RequestHeader("username") String username,
                                            @RequestHeader("access-token") String accessToken,
                                             @PathVariable int mark) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryScoreService.getScoresByMark(authorizationInfoDTO, mark);
  }
}
