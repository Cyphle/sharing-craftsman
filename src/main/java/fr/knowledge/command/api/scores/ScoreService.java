package fr.knowledge.command.api.scores;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.commands.AddScoreCommand;
import fr.knowledge.domain.scores.commands.DeleteScoreCommand;
import fr.knowledge.domain.scores.commands.UpdateScoreCommand;
import fr.knowledge.domain.scores.exceptions.AlreadyGivenScoreException;
import fr.knowledge.domain.scores.exceptions.ScoreNotFoundException;
import fr.knowledge.domain.scores.valueobjects.Mark;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
  private final AuthorizationService authorizationService;
  private final CommandBus commandBus;

  public ScoreService(AuthorizationService authorizationService, CommandBus commandBus) {
    this.authorizationService = authorizationService;
    this.commandBus = commandBus;
  }

  public ResponseEntity addScore(AuthorizationInfoDTO authorizationInfoDTO, ScoreDTO scoreDTO, String username) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO) || !authorizationService.areUsernameEquals(username, scoreDTO.getGiver()))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    AddScoreCommand command = new AddScoreCommand(scoreDTO.getGiver(), ContentType.valueOf(scoreDTO.getContentType()), scoreDTO.getContentId(), Mark.findByValue(scoreDTO.getMark()));
    try {
      commandBus.send(command);
    } catch (AlreadyGivenScoreException e) {
      return new ResponseEntity<>("Already given score.", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity updateScore(AuthorizationInfoDTO authorizationInfoDTO, ScoreDTO scoreDTO, String username) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO) || !authorizationService.areUsernameEquals(username, scoreDTO.getGiver()))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    UpdateScoreCommand command = new UpdateScoreCommand(scoreDTO.getId(), scoreDTO.getGiver(), Mark.findByValue(scoreDTO.getMark()));
    try {
      commandBus.send(command);
    } catch (ScoreNotFoundException e) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

  public ResponseEntity deleteScore(AuthorizationInfoDTO authorizationInfoDTO, ScoreDTO scoreDTO, String username) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO) || !authorizationService.areUsernameEquals(username, scoreDTO.getGiver()))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    DeleteScoreCommand command = new DeleteScoreCommand(scoreDTO.getId(), scoreDTO.getGiver());
    try {
      commandBus.send(command);
    } catch (ScoreNotFoundException e) {
      return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }
}
