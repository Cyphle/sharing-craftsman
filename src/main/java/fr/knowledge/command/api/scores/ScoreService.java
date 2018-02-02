package fr.knowledge.command.api.scores;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
  public ResponseEntity addScore(AuthorizationInfoDTO authorizationInfoDTO, ScoreDTO scoreDTO, String username) {
    return null;
  }

  public ResponseEntity updateScore(AuthorizationInfoDTO authorizationInfoDTO, ScoreDTO scoreDTO, String username) {
    return null;
  }
}
