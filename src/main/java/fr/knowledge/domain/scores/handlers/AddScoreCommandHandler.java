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

import java.util.List;
import java.util.Optional;

public class AddScoreCommandHandler implements CommandHandler {
  private final ScoreRepository scoreRepository;
  private final IdGenerator idGenerator;

  public AddScoreCommandHandler(IdGenerator idGenerator, ScoreRepository scoreRepository) {
    this.idGenerator = idGenerator;
    this.scoreRepository = scoreRepository;
  }

  @Override
  public void handle(DomainCommand command) throws AlreadyGivenScoreException {
    List<Score> scores = scoreRepository.getAll();

    boolean isScoreAlreadyGiven = scores.stream()
            .anyMatch(((AddScoreCommand) command)::hasSameProperties);
    if (isScoreAlreadyGiven)
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
