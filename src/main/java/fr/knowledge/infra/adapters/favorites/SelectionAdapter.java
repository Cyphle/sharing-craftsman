package fr.knowledge.infra.adapters.favorites;

import com.google.common.collect.Lists;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.ports.SelectionRepository;
import fr.knowledge.infra.bus.EventBus;
import fr.knowledge.infra.denormalizers.eventstore.Normalizer;
import fr.knowledge.infra.denormalizers.eventstore.favorites.SelectionDenormalizer;
import fr.knowledge.infra.denormalizers.eventstore.scores.ScoreDenormalizer;
import fr.knowledge.infra.models.EventEntity;
import fr.knowledge.infra.repositories.EventStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SelectionAdapter implements SelectionRepository {
  private final EventBus eventBus;
  private final EventStore eventStore;
  private final Normalizer normalizer;

  public SelectionAdapter(EventBus eventBus, EventStore eventStore, Normalizer normalizer) {
    this.eventBus = eventBus;
    this.eventStore = eventStore;
    this.normalizer = normalizer;
  }

  @Override
  public Optional<Selection> get(Id aggregateId) {
    List<EventEntity> events = eventStore.findByAggregateId(aggregateId.getId());
    return SelectionDenormalizer.denormalize(events);
  }

  @Override
  public List<Selection> getAll() {
    List<EventEntity> events = Lists.newArrayList(eventStore.findAll())
            .stream()
            .filter(this::isSelectionEvent)
            .collect(Collectors.toList());

    // Extract list of ids
    Set<Id> ids = events.stream()
            .map(event -> Id.of(event.getAggregateId()))
            .collect(Collectors.toSet());

    // For each id, get list of events and rebuild category
    List<Selection> selections = new ArrayList<>();
    ids.forEach(id -> {
      List<EventEntity> eventsOfCurrentAggregate = events
              .stream()
              .filter(event -> id.equals(Id.of(event.getAggregateId())))
              .collect(Collectors.toList());
      SelectionDenormalizer.denormalize(eventsOfCurrentAggregate).ifPresent(selections::add);
    });

    return selections;
  }

  @Override
  public void save(Selection selection) {
    selection.getChanges()
            .forEach(change -> {
              EventEntity event = normalizer.normalize(change);
              eventStore.save(event);
              eventBus.apply(change);
            });
  }

  private boolean isSelectionEvent(EventEntity event) {
    Pattern pattern = Pattern.compile("events.favorites.(Selection).+Event");
    Matcher matcher = pattern.matcher(event.getPayloadType());
    return matcher.find();
  }
}
