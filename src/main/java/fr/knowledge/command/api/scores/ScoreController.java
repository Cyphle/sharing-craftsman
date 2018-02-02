package fr.knowledge.command.api.scores;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scores")
@Api(description = "Endpoints to manage scores")
public class ScoreController {
  private ScoreService scoreService;

  @Autowired
  public ScoreController(ScoreService scoreService) {
    this.scoreService = scoreService;
  }

  @ApiOperation(value = "Endpoint to add a score to content", response = ResponseEntity.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity addScore(@RequestHeader("client") String client,
                                   @RequestHeader("secret") String secret,
                                   @RequestHeader("username") String username,
                                   @RequestHeader("access-token") String accessToken,
                                   @RequestBody ScoreDTO scoreDTO) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return scoreService.addScore(authorizationInfoDTO, scoreDTO, "john@doe.fr");
  }

  @ApiOperation(value = "Endpoint to update a score", response = ResponseEntity.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.PUT)
  public ResponseEntity updateScore(@RequestHeader("client") String client,
                                   @RequestHeader("secret") String secret,
                                   @RequestHeader("username") String username,
                                   @RequestHeader("access-token") String accessToken,
                                   @RequestBody ScoreDTO scoreDTO) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return scoreService.updateScore(authorizationInfoDTO, scoreDTO, "john@doe.fr");
  }
}
