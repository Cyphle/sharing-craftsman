package fr.knowledge.infra.denormalizers.library;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.infra.events.library.*;
import fr.knowledge.infra.models.EventEntity;
import fr.knowledge.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryDenormalizer {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

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
    } catch (CreateCategoryException e) {
      log.error("Error while initializing denormalization of category: " + e.getMessage());
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
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
  /*
  private void applyEvent(EventEntity event, Category category) throws IOException, KnowledgeNotFoundException {
    switch (event.getPayloadType()) {
      case "fr.knowledge.domain.library.events.KnowledgeAddedEvent":
        KnowledgeAddedEvent knowledgeAddedEvent = Mapper.fromJsonStringToObject(event.getPayload(), KnowledgeAddedEvent.class);
        if (category.is(knowledgeAddedEvent.aggregateId()))
          category.apply(knowledgeAddedEvent);
        break;
      case "fr.knowledge.domain.library.events.KnowledgeUpdatedEvent":
        KnowledgeUpdatedEvent knowledgeUpdatedEvent = Mapper.fromJsonStringToObject(event.getPayload(), KnowledgeUpdatedEvent.class);
        if (category.is(knowledgeUpdatedEvent.aggregateId()))
          category.apply(knowledgeUpdatedEvent);
        break;
      case "fr.knowledge.domain.library.events.KnowledgeDeletedEvent":
        KnowledgeDeletedEvent knowledgeDeletedEvent = Mapper.fromJsonStringToObject(event.getPayload(), KnowledgeDeletedEvent.class);
        if (category.is(knowledgeDeletedEvent.aggregateId()))
          category.apply(knowledgeDeletedEvent);
        break;
    }
  }
  */
}
