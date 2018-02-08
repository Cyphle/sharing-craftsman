package fr.knowledge.infra.denormalizers.eventstore;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.events.comments.CommentAddedInfraEvent;
import fr.knowledge.infra.events.comments.CommentDeletedInfraEvent;
import fr.knowledge.infra.events.comments.CommentUpdatedInfraEvent;
import fr.knowledge.infra.events.favorites.SelectionAddedInfraEvent;
import fr.knowledge.infra.events.favorites.SelectionRemovedInfraEvent;
import fr.knowledge.infra.events.library.*;
import fr.knowledge.infra.events.scores.ScoreCreatedInfraEvent;
import fr.knowledge.infra.events.scores.ScoreDeletedInfraEvent;
import fr.knowledge.infra.events.scores.ScoreUpdatedInfraEvent;
import fr.knowledge.infra.models.EventEntity;

import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DeserializerMapper {
  CategoryCreatedEvent("CategoryCreatedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, CategoryCreatedInfraEvent.class).fromInfraToDomain();
  }),
  CategoryUpdatedEvent("CategoryUpdatedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, CategoryUpdatedInfraEvent.class).fromInfraToDomain();
  }),
  CategoryDeletedEvent("CategoryDeletedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, CategoryDeletedInfraEvent.class).fromInfraToDomain();
  }),
  KnowledgeAddedEvent("KnowledgeAddedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, KnowledgeAddedInfraEvent.class).fromInfraToDomain();
  }),
  KnowledgeUpdatedEvent("KnowledgeUpdatedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, KnowledgeUpdatedInfraEvent.class).fromInfraToDomain();
  }),
  KnowledgeDeletedEvent("KnowledgeDeletedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, KnowledgeDeletedInfraEvent.class).fromInfraToDomain();
  }),
  CommentAddedEvent("CommentAddedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, CommentAddedInfraEvent.class).fromInfraToDomain();
  }),
  CommentUpdatedEvent("CommentUpdatedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, CommentUpdatedInfraEvent.class).fromInfraToDomain();
  }),
  CommentDeletedEvent("CommentDeletedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, CommentDeletedInfraEvent.class).fromInfraToDomain();
  }),
  ScoreCreatedEvent("ScoreCreatedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, ScoreCreatedInfraEvent.class).fromInfraToDomain();
  }),
  ScoreUpdatedEvent("ScoreUpdatedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, ScoreUpdatedInfraEvent.class).fromInfraToDomain();
  }),
  ScoreDeletedEvent("ScoreDeletedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, ScoreDeletedInfraEvent.class).fromInfraToDomain();
  }),
  SelectionAddedEvent("SelectionAddedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, SelectionAddedInfraEvent.class).fromInfraToDomain();
  }),
  SelectionRemovedEvent("SelectionRemovedInfraEvent", payload -> {
    return Mapper.fromJsonStringToObject(payload, SelectionRemovedInfraEvent.class).fromInfraToDomain();
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
