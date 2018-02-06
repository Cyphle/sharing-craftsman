package fr.knowledge.infra.denormalizers.library;

import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.infra.models.EventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CategoryDenormalizer {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private IdGenerator idGenerator;
  private EventSourcingConfig eventSourcingConfig;
  private DateService dateTimeService;

  @Autowired
  public CategoryDenormalizer(IdGenerator idGenerator, EventSourcingConfig eventSourcingConfig, DateService dateTimeService) {
    this.idGenerator = idGenerator;
    this.eventSourcingConfig = eventSourcingConfig;
    this.dateTimeService = dateTimeService;
  }

  public Optional<Category> denormalize(List<EventEntity> events) {
    events.sort(Comparator.comparing(EventEntity::getTimestamp));

    return Optional.of(Category.rebuild(events.stream()
            .map(DeserializerMapper::deserialize)
            .collect(Collectors.toList())));
  }

  public EventEntity normalize(DomainEvent change) {
    return new EventEntity(
            idGenerator.generate(),
            eventSourcingConfig.getVersion(),
            dateTimeService.nowInDate(),
            change.getAggregateId(),
            getInfraEventClass(change.getClass().getName()),
            SerializerMapper.serialize(change)
    );
  }

  private String getInfraEventClass(String domainEventClassName) {
    Pattern pattern = Pattern.compile("events.(.+)Event");
    Matcher matcher = pattern.matcher(domainEventClassName);
    return matcher.find() ? "fr.knowledge.infra.events.library." + matcher.group(1) + "InfraEvent" : "";
  }
}
