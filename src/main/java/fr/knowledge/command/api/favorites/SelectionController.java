package fr.knowledge.command.api.favorites;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selections")
@Api(description = "Endpoints to manage favorites")
public class SelectionController {
  private SelectionService selectionService;

  @Autowired
  public SelectionController(SelectionService selectionService) {
    this.selectionService = selectionService;
  }

  @ApiOperation(value = "Endpoint to add a selection to user", response = ResponseEntity.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity addToMySelection(@RequestHeader("client") String client,
                                       @RequestHeader("secret") String secret,
                                       @RequestHeader("username") String username,
                                       @RequestHeader("access-token") String accessToken,
                                       @RequestBody SelectionDTO selectionDTO) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return selectionService.addSelection(authorizationInfoDTO, selectionDTO);
  }
}
