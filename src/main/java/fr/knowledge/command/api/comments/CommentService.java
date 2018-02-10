package fr.knowledge.command.api.comments;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.comments.commands.AddCommentCommand;
import fr.knowledge.domain.comments.commands.DeleteCommentCommand;
import fr.knowledge.domain.comments.commands.UpdateCommentCommand;
import fr.knowledge.domain.comments.exceptions.CommentNotFoundException;
import fr.knowledge.domain.comments.exceptions.CommentException;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
  private final AuthorizationService authorizationService;
  private final CommandBus commandBus;

  @Autowired
  public CommentService(AuthorizationService authorizationService, CommandBus commandBus) {
    this.authorizationService = authorizationService;
    this.commandBus = commandBus;
  }

  public ResponseEntity addComment(AuthorizationInfoDTO authorizationInfoDTO, CommentDTO commentDTO, String username) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO) || !authorizationService.areUsernameEquals(username, commentDTO.getCommenter()))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    AddCommentCommand command = new AddCommentCommand(commentDTO.getCommenter(), ContentType.valueOf(commentDTO.getContentType()), commentDTO.getContentId(), commentDTO.getContent());
    try {
      commandBus.send(command);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity updateComment(AuthorizationInfoDTO authorizationInfoDTO, CommentDTO commentDTO, String username) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO) || !authorizationService.areUsernameEquals(username, commentDTO.getCommenter()))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    UpdateCommentCommand command = new UpdateCommentCommand(commentDTO.getId(), commentDTO.getCommenter(), commentDTO.getContent());
    return sendCommand(command);
  }

  public ResponseEntity deleteComment(AuthorizationInfoDTO authorizationInfoDTO, CommentDTO commentDTO, String username) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO) || !authorizationService.areUsernameEquals(username, commentDTO.getCommenter()))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    DeleteCommentCommand command = new DeleteCommentCommand(commentDTO.getId(), commentDTO.getCommenter());
    return sendCommand(command);
  }

  private ResponseEntity sendCommand(DomainCommand command) {
    try {
      commandBus.send(command);
    } catch (CommentNotFoundException e) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } catch (CommentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }
}
