package fr.knowledge.command.api.comments;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@Api(description = "Endpoints to manage commenst")
public class CommentController {
  private CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @ApiOperation(value = "Endpoint to add a comment to content", response = ResponseEntity.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity addComment(@RequestHeader("client") String client,
                                         @RequestHeader("secret") String secret,
                                         @RequestHeader("username") String username,
                                         @RequestHeader("access-token") String accessToken,
                                         @RequestBody CommentDTO commentDTO) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return commentService.addComment(authorizationInfoDTO, commentDTO);
  }

  @ApiOperation(value = "Endpoint to update a comment to content", response = ResponseEntity.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.PUT)
  public ResponseEntity updateComment(@RequestHeader("client") String client,
                                         @RequestHeader("secret") String secret,
                                         @RequestHeader("username") String username,
                                         @RequestHeader("access-token") String accessToken,
                                         @RequestBody CommentDTO commentDTO) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return commentService.updateComment(authorizationInfoDTO, commentDTO);
  }
}
