package fr.knowledge.infra.denormalizers.comments;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.infra.denormalizers.DeserializerMapper;
import fr.knowledge.infra.models.EventEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentDenormalizer {
  public static Optional<Comment> denormalize(List<EventEntity> events) {
    if (events.isEmpty())
      return Optional.empty();

    events.sort(Comparator.comparing(EventEntity::getTimestamp));

    return Optional.of(Comment.rebuild(events.stream()
            .map(DeserializerMapper::deserialize)
            .collect(Collectors.toList())));
  }
}
