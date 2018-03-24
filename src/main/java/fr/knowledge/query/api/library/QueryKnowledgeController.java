package fr.knowledge.query.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@Api(description = "Endpoints to get knowledges")
public class QueryKnowledgeController {
  private QueryKnowledgeService queryKnowledgeService;

  @Autowired
  public QueryKnowledgeController(QueryKnowledgeService queryKnowledgeService) {
    this.queryKnowledgeService = queryKnowledgeService;
  }

  @ApiOperation(value = "Endpoint to get one knowledge by id", response = KnowledgeElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET, value = "/knowledges/{knowledgeId}")
  public ResponseEntity getKnowledgeById(@RequestHeader("client") String client,
                                        @RequestHeader("secret") String secret,
                                        @RequestHeader("username") String username,
                                        @RequestHeader("access-token") String accessToken,
                                        @PathVariable String knowledgeId) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryKnowledgeService.getKnowledgeById(authorizationInfoDTO, knowledgeId);
  }
}
