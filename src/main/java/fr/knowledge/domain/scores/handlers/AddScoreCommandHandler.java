package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.AddScoreCommand;
import fr.knowledge.domain.scores.exceptions.AlreadyGivenScoreException;
import fr.knowledge.domain.scores.ports.ScoreRepository;

import java.util.Optional;

class AddScoreCommandHandler implements CommandHandler {
  private final ScoreRepository scoreRepository;
  private final IdGenerator idGenerator;

  public AddScoreCommandHandler(IdGenerator idGenerator, ScoreRepository scoreRepository) {
    this.idGenerator = idGenerator;
    this.scoreRepository = scoreRepository;
  }

  @Override
  public void handle(DomainCommand command) throws AlreadyGivenScoreException {
    Optional<Score> score = scoreRepository.get(Username.from(((AddScoreCommand) command).getGiver()), Id.of(((AddScoreCommand) command).getContentId()));

    if (score.isPresent())
      throw new AlreadyGivenScoreException();

    Score newScore = Score.newScore(
            idGenerator.generate(),
            ((AddScoreCommand) command).getGiver(),
            ((AddScoreCommand) command).getContentType(),
            ((AddScoreCommand) command).getContentId(),
            ((AddScoreCommand) command).getMark()
    );
    scoreRepository.save(newScore);
  }
}
