package fr.knowledge.infra.denormalizers.eventstore;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.events.comments.CommentAddedInfraEvent;
import fr.knowledge.infra.events.comments.CommentDeletedInfraEvent;
import fr.knowledge.infra.events.comments.CommentUpdatedInfraEvent;
import fr.knowledge.infra.events.library.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SerializerMapper {
  CategoryCreatedEvent("CategoryCreatedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CategoryCreatedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.library.CategoryCreatedInfraEvent"),
  CategoryUpdatedEvent("CategoryUpdatedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CategoryUpdatedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event event: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.library.CategoryUpdatedInfraEvent"),
  CategoryDeletedEvent("CategoryDeletedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CategoryDeletedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event event: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.library.CategoryDeletedInfraEvent"),
  KnowledgeAddedEvent("KnowledgeAddedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(KnowledgeAddedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.library.KnowledgeAddedInfraEvent"),
  KnowledgeUpdatedEvent("KnowledgeUpdatedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(KnowledgeUpdatedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.library.KnowledgeUpdatedInfraEvent"),
  KnowledgeDeletedEvent("KnowledgeDeletedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(KnowledgeDeletedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.library.KnowledgeDeletedInfraEvent"),
  CommentAddedEvent("CommentAddedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CommentAddedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.comments.CommentAddedInfraEvent"),
  CommentUpdatedEvent("CommentUpdatedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CommentUpdatedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.comments.CommentUpdatedInfraEvent"),
  CommentDeletedEvent("CommentDeletedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CommentDeletedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }, "fr.knowledge.infra.events.comments.CommentDeletedInfraEvent");

  public final String eventClass;
  public final Function<DomainEvent, String> serializer;
  private String eventFullClass;

  SerializerMapper(String eventClass, Function<DomainEvent, String> serializer, String eventFullClass) {
    this.eventClass = eventClass;
    this.serializer = serializer;
    this.eventFullClass = eventFullClass;
  }

  public static String serialize(DomainEvent change) {
    Pattern pattern = Pattern.compile("(.+)Event");
    Matcher matcher = pattern.matcher(change.getClass().getSimpleName());
    if (matcher.find()) {
      return Arrays.stream(values())
              .filter(serializer -> {
                String group = matcher.group(1);
                return serializer.eventClass.equals(group + "InfraEvent");
              })
              .findAny()
              .orElseThrow(RuntimeException::new).serializer.apply(change);
    } else {
      throw new RuntimeException("No serializer for: " + change.getClass().getName());
    }
  }

  public static String getInfraEventClass(String eventClass) {
    Pattern pattern = Pattern.compile("(.+)Event");
    Matcher matcher = pattern.matcher(eventClass);
    if (matcher.find()) {
      return Arrays.stream(values())
              .filter(serializer -> serializer.eventClass.equals(matcher.group(1) + "InfraEvent"))
              .findAny()
              .orElseThrow(RuntimeException::new).eventFullClass;
    } else {
      return "";
    }
  }
}
