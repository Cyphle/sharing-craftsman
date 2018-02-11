package fr.knowledge.query.api.comments;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.infra.models.comments.CommentElastic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@Api(description = "Endpoints to get comments")
public class QueryCommentsController {
  private QueryCommentService queryCommentService;

  @Autowired
  public QueryCommentsController(QueryCommentService queryCommentService) {
    this.queryCommentService = queryCommentService;
  }

  @ApiOperation(value = "Endpoint to comments for a given content", response = CommentElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET, value = "{contentId}")
  public ResponseEntity getCommentsForContent(@RequestHeader("client") String client,
                                              @RequestHeader("secret") String secret,
                                              @RequestHeader("username") String username,
                                              @RequestHeader("access-token") String accessToken,
                                              @PathVariable String contentId) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryCommentService.getCommentsForContent(authorizationInfoDTO, contentId);
  }
}
