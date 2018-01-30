package fr.knowledge.command.api.library;

import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.exceptions.AlreadyExistingCategoryException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.remote.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
  private CommandBus commandBus;
  private UserService userService;

  @Autowired
  public LibraryService(UserService userService, CommandBus commandBus) {
    this.userService = userService;
    this.commandBus = commandBus;
  }

  public ResponseEntity createCategory(CategoryDTO categoryDTO) {
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
