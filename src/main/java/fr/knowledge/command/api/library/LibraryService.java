package fr.knowledge.command.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.exceptions.AlreadyExistingCategoryException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
  private CommandBus commandBus;
  private AuthorizationService authorizationService;

  @Autowired
  public LibraryService(AuthorizationService authorizationService, CommandBus commandBus) {
    this.authorizationService = authorizationService;
    this.commandBus = commandBus;
  }

  public ResponseEntity createCategory(AuthorizationInfoDTO authorizationInfoDTO, CategoryDTO categoryDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);

    CreateCategoryCommand command = new CreateCategoryCommand(categoryDTO.getName());
    try {
      commandBus.send(command);
    } catch (AlreadyExistingCategoryException e) {
      return ResponseEntity.badRequest().body("Category already exists.");
    } catch (CreateCategoryException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }
}
