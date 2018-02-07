package fr.knowledge.infra.denormalizers.eventstore.library;

import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.infra.denormalizers.eventstore.DeserializerMapper;
import fr.knowledge.infra.models.EventEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryDenormalizer {
  public static Optional<Category> denormalize(List<EventEntity> events) {
    events.sort(Comparator.comparing(EventEntity::getTimestamp));

    return Optional.of(Category.rebuild(events.stream()
            .map(DeserializerMapper::deserialize)
            .collect(Collectors.toList())));
  }

}
