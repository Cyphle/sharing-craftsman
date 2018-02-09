package fr.knowledge.infra.denormalizers.eventstore.scores;

import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.infra.denormalizers.eventstore.DeserializerMapper;
import fr.knowledge.infra.models.EventEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScoreDenormalizer {
  public static Optional<Score> denormalize(List<EventEntity> events) {
    if (events.isEmpty())
      return Optional.empty();

    events.sort(Comparator.comparing(EventEntity::getTimestamp));

    return Optional.of(Score.rebuild(events.stream()
            .map(DeserializerMapper::deserialize)
            .collect(Collectors.toList())));
  }
}
