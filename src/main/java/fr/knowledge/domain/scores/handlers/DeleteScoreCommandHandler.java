package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.DeleteScoreCommand;
import fr.knowledge.domain.scores.exceptions.ScoreNotFoundException;
import fr.knowledge.domain.scores.ports.ScoreRepository;

class DeleteScoreCommandHandler {
  private final ScoreRepository scoreRepository;

  public DeleteScoreCommandHandler(ScoreRepository scoreRepository) {
    this.scoreRepository = scoreRepository;
  }

  public void handle(DeleteScoreCommand command) throws ScoreNotFoundException {
    Score score = scoreRepository.get(Id.of(command.getId()), Username.from(command.getGiver()))
            .orElseThrow(ScoreNotFoundException::new);
    score.delete();
    scoreRepository.save(score);
  }
}
