package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.UpdateScoreCommand;
import fr.knowledge.domain.scores.exceptions.ScoreNotFoundException;
import fr.knowledge.domain.scores.ports.ScoreRepository;

class UpdateScoreCommandHandler {
  private final ScoreRepository scoreRepository;

  public UpdateScoreCommandHandler(ScoreRepository scoreRepository) {
    this.scoreRepository = scoreRepository;
  }

  public void handle(UpdateScoreCommand command) throws ScoreNotFoundException {
    Score score = scoreRepository.get(Id.of(command.getId()), Username.from(command.getGiver()))
            .orElseThrow(ScoreNotFoundException::new);
    score.update(command.getMark());
    scoreRepository.save(score);
  }
}
