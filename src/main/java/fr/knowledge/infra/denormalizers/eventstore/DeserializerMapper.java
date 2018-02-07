package fr.knowledge.infra.denormalizers.eventstore;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.events.comments.CommentAddedInfraEvent;
import fr.knowledge.infra.events.comments.CommentDeletedInfraEvent;
import fr.knowledge.infra.events.comments.CommentUpdatedInfraEvent;
import fr.knowledge.infra.events.library.*;
import fr.knowledge.infra.models.EventEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DeserializerMapper {
  CategoryCreatedEvent("CategoryCreatedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, CategoryCreatedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  CategoryUpdatedEvent("CategoryUpdatedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, CategoryUpdatedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  CategoryDeletedEvent("CategoryDeletedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, CategoryDeletedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  KnowledgeAddedEvent("KnowledgeAddedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, KnowledgeAddedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  KnowledgeUpdatedEvent("KnowledgeUpdatedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, KnowledgeUpdatedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  KnowledgeDeletedEvent("KnowledgeDeletedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, KnowledgeDeletedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  CommentAddedEvent("CommentAddedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, CommentAddedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  CommentUpdatedEvent("CommentUpdatedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, CommentUpdatedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  CommentDeletedEvent("CommentDeletedInfraEvent", payload -> {
    try {
      return Mapper.fromJsonStringToObject(payload, CommentDeletedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  });

  public final Function<String, DomainEvent> deserializer;
  public final String eventClass;

  DeserializerMapper(String eventClass, Function<String, DomainEvent> deserializer) {
    this.eventClass = eventClass;
    this.deserializer = deserializer;
  }

  public static DomainEvent deserialize(EventEntity event) {
    Pattern pattern = Pattern.compile("events\\..+\\.(.+Event)");
    Matcher matcher = pattern.matcher(event.getPayloadType());
    if (matcher.find()) {
      return Arrays.stream(values())
              .filter(deserializer1 -> deserializer1.eventClass.equals(matcher.group(1)))
              .findAny()
              .orElseThrow(RuntimeException::new).deserializer.apply(event.getPayload());
    } else {
      throw new RuntimeException("No deserializer for: " + event.getPayloadType());
    }
  }
}
