package fr.knowledge.query.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.infra.models.library.CategoryElastic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@Api(description = "Endpoints to get categories and knowledges")
public class QueryCategoryController {
  private final QueryCategoryService queryCategoryService;

  @Autowired
  public QueryCategoryController(QueryCategoryService queryCategoryService) {
    this.queryCategoryService = queryCategoryService;
  }

  @ApiOperation(value = "Endpoint to get all categories", response = CategoryElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity getAllCategories(@RequestHeader("client") String client,
                                         @RequestHeader("secret") String secret,
                                         @RequestHeader("username") String username,
                                         @RequestHeader("access-token") String accessToken) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryCategoryService.getAllCategories(authorizationInfoDTO);
  }

  @ApiOperation(value = "Endpoint to get one category by id", response = CategoryElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.GET, value = "{categoryId}")
  public ResponseEntity getCategoryById(@RequestHeader("client") String client,
                                        @RequestHeader("secret") String secret,
                                        @RequestHeader("username") String username,
                                        @RequestHeader("access-token") String accessToken,
                                        @PathVariable String categoryId) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryCategoryService.getCategoryById(authorizationInfoDTO, categoryId);
  }

  @ApiOperation(value = "Endpoint to get one category by id", response = CategoryElastic.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.POST, value = "/search")
  public ResponseEntity searchForCategories(@RequestHeader("client") String client,
                                        @RequestHeader("secret") String secret,
                                        @RequestHeader("username") String username,
                                        @RequestHeader("access-token") String accessToken,
                                        @RequestBody SearchCriteriaDTO searchCriteriaDTO) {
    AuthorizationInfoDTO authorizationInfoDTO = new AuthorizationInfoDTO(client, secret, username, accessToken);
    return queryCategoryService.searchCategories(authorizationInfoDTO, searchCriteriaDTO);
  }
}
