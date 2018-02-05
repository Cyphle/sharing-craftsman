package fr.knowledge.infra.denormalizers.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.common.Mapper;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.exceptions.CategoryException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.infra.events.library.*;
import fr.knowledge.infra.models.EventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    if (isCategoryDeleted(events))
      return Optional.empty();

    events.sort(Comparator.comparing(EventEntity::getTimestamp));
    try {
      Category category = Category.newCategory("temp", "temp");
      events.forEach(event -> {
        try {
          applyEvent(event, category);
        } catch (IOException | KnowledgeNotFoundException e) {
          log.error("Error while parsing event payload: " + e.getClass().getName() + ", " + e.getMessage());
          throw new RuntimeException("Error while parsing event payload: " + e.getClass().getName() + ", " + e.getMessage());
        }
      });
      category.resetChanges();
      return Optional.of(category);
    } catch (CategoryException e) {
      log.error("Error while initializing denormalization of category: " + e.getMessage());
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }

  public EventEntity normalize(DomainEvent change) {
    try {
      return new EventEntity(
              idGenerator.generate(),
              eventSourcingConfig.getVersion(),
              dateTimeService.nowInDate(),
              change.getAggregateId(),
              getInfraEventClass(change.getClass().getName()),
              convertDomainEventToInfraEvent(change)
      );
    } catch (JsonProcessingException e) {
      log.error("Error while converting to infra event: " + e.getMessage());
      throw new RuntimeException("Error while converting to infra event: " + e.getMessage());
    }
  }

  private String getInfraEventClass(String domainEventClassName) {
    Pattern pattern = Pattern.compile("events.(.+)Event");
    Matcher matcher = pattern.matcher(domainEventClassName);
    return matcher.find() ? "fr.knowledge.infra.events.library." + matcher.group(1) + "InfraEvent" : "";
  }

  private String convertDomainEventToInfraEvent(DomainEvent domainEvent) throws JsonProcessingException {
    switch (domainEvent.getClass().getName()) {
      case "fr.knowledge.domain.library.events.CategoryCreatedEvent":
        return Mapper.fromObjectToJsonString(CategoryCreatedInfraEvent.fromDomainToInfra(domainEvent));
      case "fr.knowledge.domain.library.events.CategoryUpdatedEvent":
        return Mapper.fromObjectToJsonString(CategoryUpdatedInfraEvent.fromDomainToInfra(domainEvent));
      case "fr.knowledge.domain.library.events.KnowledgeAddedEvent":
        return Mapper.fromObjectToJsonString(KnowledgeAddedInfraEvent.fromDomainToInfra(domainEvent));
      case "fr.knowledge.domain.library.events.KnowledgeUpdatedEvent":
        return Mapper.fromObjectToJsonString(KnowledgeUpdatedInfraEvent.fromDomainToInfra(domainEvent));
      case "fr.knowledge.domain.library.events.KnowledgeDeletedEvent":
        return Mapper.fromObjectToJsonString(KnowledgeDeletedInfraEvent.fromDomainToInfra(domainEvent));
      default:
        return "";
    }
  }

  private void applyEvent(EventEntity event, Category category) throws IOException, KnowledgeNotFoundException {
    switch (event.getPayloadType()) {
      case "fr.knowledge.infra.events.library.CategoryCreatedInfraEvent":
        CategoryCreatedInfraEvent categoryCreatedInfraEvent = Mapper.fromJsonStringToObject(event.getPayload(), CategoryCreatedInfraEvent.class);
        category.apply(categoryCreatedInfraEvent.fromInfraToDomain());
        break;
      case "fr.knowledge.infra.events.library.CategoryUpdatedInfraEvent":
        CategoryUpdatedInfraEvent categoryUpdatedInfraEvent = Mapper.fromJsonStringToObject(event.getPayload(), CategoryUpdatedInfraEvent.class);
        if (category.is(Id.of(categoryUpdatedInfraEvent.getId())))
          category.apply(categoryUpdatedInfraEvent.fromInfraToDomain());
        break;
      case "fr.knowledge.infra.events.library.KnowledgeAddedInfraEvent":
        KnowledgeAddedInfraEvent knowledgeAddedInfraEvent = Mapper.fromJsonStringToObject(event.getPayload(), KnowledgeAddedInfraEvent.class);
        if (category.is(Id.of(knowledgeAddedInfraEvent.getCategoryId())))
          category.apply(knowledgeAddedInfraEvent.fromInfraToDomain());
        break;
      case "fr.knowledge.infra.events.library.KnowledgeUpdatedInfraEvent":
        KnowledgeUpdatedInfraEvent knowledgeUpdatedInfraEvent = Mapper.fromJsonStringToObject(event.getPayload(), KnowledgeUpdatedInfraEvent.class);
        if (category.is(Id.of(knowledgeUpdatedInfraEvent.getCategoryId())))
          category.apply(knowledgeUpdatedInfraEvent.fromInfraToDomain());
        break;
      case "fr.knowledge.infra.events.library.KnowledgeDeletedInfraEvent":
        KnowledgeDeletedInfraEvent knowledgeDeletedInfraEvent = Mapper.fromJsonStringToObject(event.getPayload(), KnowledgeDeletedInfraEvent.class);
        if (category.is(Id.of(knowledgeDeletedInfraEvent.getCategoryId())))
          category.apply(knowledgeDeletedInfraEvent.fromInfraToDomain());
        break;
    }
  }

  private boolean isCategoryDeleted(List<EventEntity> events) {
    return events.stream()
            .anyMatch(event -> event.getPayloadType().equals("fr.knowledge.infra.events.library.CategoryDeletedInfraEvent"));
  }
}
