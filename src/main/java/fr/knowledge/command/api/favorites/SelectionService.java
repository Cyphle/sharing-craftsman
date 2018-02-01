package fr.knowledge.command.api.favorites;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.favorites.commands.AddToMySelectionCommand;
import fr.knowledge.domain.favorites.exceptions.AlreadyExistingSelectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SelectionService {
  private final AuthorizationService authorizationService;
  private final CommandBus commandBus;

  @Autowired
  public SelectionService(AuthorizationService authorizationService, CommandBus commandBus) {
    this.authorizationService = authorizationService;
    this.commandBus = commandBus;
  }

  public ResponseEntity addSelection(AuthorizationInfoDTO authorizationInfoDTO, SelectionDTO selectionDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    AddToMySelectionCommand command = new AddToMySelectionCommand(selectionDTO.getUsername(), ContentType.valueOf(selectionDTO.getContentType().toUpperCase()), selectionDTO.getContentId());
    try {
      commandBus.send(command);
    } catch (AlreadyExistingSelectionException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity removeSelection(AuthorizationInfoDTO authorizationInfoDTO, SelectionDTO selectionDTO) {
    return null;
  }
}
