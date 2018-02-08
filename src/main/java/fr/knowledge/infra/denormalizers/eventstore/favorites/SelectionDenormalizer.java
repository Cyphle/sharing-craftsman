package fr.knowledge.infra.denormalizers.eventstore.favorites;

import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.infra.denormalizers.eventstore.DeserializerMapper;
import fr.knowledge.infra.models.EventEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SelectionDenormalizer {

  public Optional<Selection> denormalize(List<EventEntity> events) {
    if (events.isEmpty())
      return Optional.empty();

    events.sort(Comparator.comparing(EventEntity::getTimestamp));

    return Optional.of(Selection.rebuild(events.stream()
            .map(DeserializerMapper::deserialize)
            .collect(Collectors.toList())));
  }
}
