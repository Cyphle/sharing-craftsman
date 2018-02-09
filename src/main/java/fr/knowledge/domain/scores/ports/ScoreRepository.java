package fr.knowledge.domain.scores.ports;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository {
  Optional<Score> get(Id aggregateId);

  List<Score> getAll();

  void save(Score score);
}
