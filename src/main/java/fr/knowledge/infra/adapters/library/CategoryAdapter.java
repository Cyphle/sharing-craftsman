package fr.knowledge.infra.adapters.library;

import com.google.common.collect.Lists;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.infra.bus.EventBus;
import fr.knowledge.infra.denormalizers.library.CategoryDenormalizer;
import fr.knowledge.infra.models.EventEntity;
import fr.knowledge.infra.repositories.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CategoryAdapter implements CategoryRepository {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final EventBus eventBus;
  private final EventStore eventStore;
  private CategoryDenormalizer categoryDenormalizer;
  private IdGenerator idGenerator;
  private EventSourcingConfig eventSourcingConfig;
  private DateService dateTimeService;

  @Autowired
  public CategoryAdapter(
          EventBus eventBus,
          EventStore eventStore,
          CategoryDenormalizer categoryDenormalizer,
          IdGenerator idGenerator,
          EventSourcingConfig eventSourcingConfig,
          DateService dateTimeService) {
    this.eventBus = eventBus;
    this.eventStore = eventStore;
    this.categoryDenormalizer = categoryDenormalizer;
    this.idGenerator = idGenerator;
    this.eventSourcingConfig = eventSourcingConfig;
    this.dateTimeService = dateTimeService;
  }

  @Override
  public List<Category> getAll() {
    List<EventEntity> events = Lists.newArrayList(eventStore.findAll())
            .stream()
            .filter(this::isACategoryEvent)
            .collect(Collectors.toList());

    // Extract list of ids
    Set<Id> ids = events.stream()
            .map(event -> Id.of(event.getAggregateId()))
            .collect(Collectors.toSet());

    // For each id, get list of events and rebuild category
    List<Category> categories = new ArrayList<>();
    ids.forEach(id -> {
      List<EventEntity> eventsOfCurrentAggregate = events
              .stream()
              .filter(event -> id.equals(Id.of(event.getAggregateId())))
              .collect(Collectors.toList());
      categoryDenormalizer.denormalize(eventsOfCurrentAggregate).ifPresent(categories::add);
    });

    return categories;
  }

  @Override
  public void save(Category category) {
//    category.getChanges()
//            .forEach(change -> {
//              try {
//                EventEntity event = new EventEntity(
//                        idGenerator.generate(),
//                        eventSourcingConfig.getVersion(),
//                        dateTimeService.nowInDate(),
//                        change.aggregateId().getId(),
//                        change.getClass().getName(),
//                        Mapper.fromObjectToJsonString(change)
//                );
//                eventStore.save(event);
//                // TODO
//                // eventBus.apply(change);
//              } catch (JsonProcessingException e) {
//                log.error("Error while stringifying event: " + e.getMessage());
//                throw new RuntimeException("Error while stringifying event: " + e.getMessage());
//              }
//            });
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<Category> get(Id aggregateId) {
    List<EventEntity> events = eventStore.findByAggregateId(aggregateId.getId());
    return categoryDenormalizer.denormalize(events);
  }

  private boolean isACategoryEvent(EventEntity event) {
    Pattern pattern = Pattern.compile("events.library.(Category|Knowledge).+Event");
    Matcher matcher = pattern.matcher(event.getPayloadType());
    return matcher.find();
  }
}
