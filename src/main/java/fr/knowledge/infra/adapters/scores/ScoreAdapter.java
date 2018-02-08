package fr.knowledge.infra.adapters.scores;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.ports.ScoreRepository;

import java.util.Optional;

public class ScoreAdapter implements ScoreRepository {
  @Override
  public Optional<Score> get(Id id) {
    return Optional.empty();
  }

  @Override
  public Optional<Score> get(Username giver, Id contentId) {
    return Optional.empty();
  }

  @Override
  public void save(Score score) {

  }
}
