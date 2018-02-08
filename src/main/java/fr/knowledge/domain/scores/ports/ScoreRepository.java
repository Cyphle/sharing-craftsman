package fr.knowledge.domain.scores.ports;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;

import java.util.Optional;

public interface ScoreRepository {
  Optional<Score> get(Id id);

  Optional<Score> get(Username giver, Id contentId);

  void save(Score score);
}
