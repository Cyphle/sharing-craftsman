package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.AddScoreCommand;
import fr.knowledge.domain.scores.exceptions.AlreadyGivenScoreException;
import fr.knowledge.domain.scores.ports.ScoreRepository;

import java.util.Optional;

class AddScoreCommandHandler {
  private final ScoreRepository scoreRepository;
  private final IdGenerator idGenerator;

  public AddScoreCommandHandler(IdGenerator idGenerator, ScoreRepository scoreRepository) {
    this.idGenerator = idGenerator;
    this.scoreRepository = scoreRepository;
  }

  public void handle(AddScoreCommand command) throws AlreadyGivenScoreException {
    Optional<Score> score = scoreRepository.get(Username.from(command.getGiver()), Id.of(command.getContentId()));

    if (score.isPresent())
      throw new AlreadyGivenScoreException();

    Score newScore = Score.newScore(idGenerator.generate(), command.getGiver(), command.getContentType(), command.getContentId(), command.getMark());
    scoreRepository.save(newScore);
  }
}
