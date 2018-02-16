package fr.knowledge.infra.adapters.comments;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.infra.bus.EventBus;
import fr.knowledge.infra.denormalizers.Normalizer;
import fr.knowledge.infra.denormalizers.comments.CommentDenormalizer;
import fr.knowledge.infra.models.EventEntity;
import fr.knowledge.infra.repositories.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentAdapter implements CommentRepository {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final EventBus eventBus;
  private final EventStore eventStore;
  private final Normalizer normalizer;

  @Autowired
  public CommentAdapter(EventBus eventBus, EventStore eventStore, Normalizer normalizer) {
    this.eventBus = eventBus;
    this.eventStore = eventStore;
    this.normalizer = normalizer;
  }

  @Override
  public Optional<Comment> get(Id aggregateId) {
    List<EventEntity> events = eventStore.findByAggregateId(aggregateId.getId());
    return CommentDenormalizer.denormalize(events);
  }

  @Override
  public void save(Comment comment) {
    comment.getChanges()
            .forEach(change -> {
              EventEntity event = normalizer.normalize(change);
              log.info("[CommentAdapter::save] -- Save infrastructure event");
              eventStore.save(event);
              eventBus.apply(change);
            });
  }
}
