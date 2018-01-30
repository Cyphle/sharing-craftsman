package fr.knowledge.command.api.library;

import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
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
    commandBus.send(command);
    return ResponseEntity.ok().build();
  }
}
