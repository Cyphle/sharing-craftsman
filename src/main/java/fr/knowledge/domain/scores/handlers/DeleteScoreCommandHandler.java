package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.DeleteScoreCommand;
import fr.knowledge.domain.scores.exceptions.ScoreException;
import fr.knowledge.domain.scores.exceptions.ScoreNotFoundException;
import fr.knowledge.domain.scores.ports.ScoreRepository;

class DeleteScoreCommandHandler implements CommandHandler {
  private final ScoreRepository scoreRepository;

  public DeleteScoreCommandHandler(ScoreRepository scoreRepository) {
    this.scoreRepository = scoreRepository;
  }

  @Override
  public void handle(DomainCommand command) throws ScoreNotFoundException, ScoreException {
    Score score = scoreRepository.get(Id.of(((DeleteScoreCommand) command).getId()))
            .orElseThrow(ScoreNotFoundException::new);
    score.delete(Username.from(((DeleteScoreCommand) command).getGiver()));
    scoreRepository.save(score);
  }
}
