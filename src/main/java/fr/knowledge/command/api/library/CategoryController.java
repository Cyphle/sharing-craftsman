package fr.knowledge.command.api.library;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
@Api(description = "Endpoints to interact with the knowledge library categories")
public class CategoryController {
  private LibraryService libraryService;

  @Autowired
  public CategoryController(LibraryService libraryService) {
    this.libraryService = libraryService;
  }

  @ApiOperation(value = "Endpoint to create a new category", response = ResponseEntity.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = ""),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @RequestMapping(method = RequestMethod.POST, value = "/category")
  public ResponseEntity registerUser(@RequestBody CategoryDTO categoryDTO) {
    return libraryService.createCategory(categoryDTO);
  }
}
