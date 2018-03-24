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
  private final QueryCommentService queryCommentService;

  @Autowired
  public QueryCommentsController(QueryCommentService queryCommentService) {
    this.queryCommentService = queryCommentService;
  }

  @ApiOperation(value = "Endpoint to get comments for a given content", response = CommentElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET, value = "/contentId/{contentId}")
  public ResponseEntity getCommentsForContent(@PathVariable String contentId) {
    return queryCommentService.getCommentsForContent(contentId);
  }

  @ApiOperation(value = "Endpoint to get a comment by id", response = CommentElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET, value = "/{commentId}")
  public ResponseEntity getCommentsById(@RequestHeader("client") String client,
                                              @RequestHeader("secret") String secret,
                                              @RequestHeader("username") String username,
                                              @RequestHeader("access-token") String accessToken,
                                              @PathVariable String commentId) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryCommentService.getCommentById(authorizationInfoDTO, commentId);
  }
}
