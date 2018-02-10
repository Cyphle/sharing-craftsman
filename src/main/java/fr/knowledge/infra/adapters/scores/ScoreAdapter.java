package fr.knowledge.infra.adapters.scores;

import com.google.common.collect.Lists;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.ports.ScoreRepository;
import fr.knowledge.infra.bus.EventBus;
import fr.knowledge.infra.denormalizers.Normalizer;
import fr.knowledge.infra.denormalizers.scores.ScoreDenormalizer;
import fr.knowledge.infra.models.EventEntity;
import fr.knowledge.infra.repositories.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ScoreAdapter implements ScoreRepository {
  private final EventBus eventBus;
  private final EventStore eventStore;
  private final Normalizer normalizer;

  @Autowired
  public ScoreAdapter(EventBus eventBus, EventStore eventStore, Normalizer normalizer) {
    this.eventBus = eventBus;
    this.eventStore = eventStore;
    this.normalizer = normalizer;
  }

  @Override
  public Optional<Score> get(Id aggregateId) {
    List<EventEntity> events = eventStore.findByAggregateId(aggregateId.getId());
    return ScoreDenormalizer.denormalize(events);
  }

  @Override
  public List<Score> getAll() {
    List<EventEntity> events = Lists.newArrayList(eventStore.findAll())
            .stream()
            .filter(this::isScoreEvent)
            .collect(Collectors.toList());

    // Extract list of ids
    Set<Id> ids = events.stream()
            .map(event -> Id.of(event.getAggregateId()))
            .collect(Collectors.toSet());

    // For each id, get list of events and rebuild category
    List<Score> scores = new ArrayList<>();
    ids.forEach(id -> {
      List<EventEntity> eventsOfCurrentAggregate = events
              .stream()
              .filter(event -> id.equals(Id.of(event.getAggregateId())))
              .collect(Collectors.toList());
      ScoreDenormalizer.denormalize(eventsOfCurrentAggregate).ifPresent(scores::add);
    });

    return scores;
  }

  @Override
  public void save(Score score) {
    score.getChanges()
            .forEach(change -> {
              EventEntity event = normalizer.normalize(change);
              eventStore.save(event);
              eventBus.apply(change);
            });
  }

  private boolean isScoreEvent(EventEntity event) {
    Pattern pattern = Pattern.compile("events.scores.(Score).+Event");
    Matcher matcher = pattern.matcher(event.getPayloadType());
    return matcher.find();
  }
}
