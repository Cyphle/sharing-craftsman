package fr.knowledge.infra.denormalizers.library;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
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
  }),
  CategoryUpdatedEvent("CategoryUpdatedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CategoryUpdatedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event event: " + e.getMessage());
    }
  }),
  CategoryDeletedEvent("CategoryDeletedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(CategoryDeletedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event event: " + e.getMessage());
    }
  }),
  KnowledgeAddedEvent("KnowledgeAddedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(KnowledgeAddedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  KnowledgeUpdatedEvent("KnowledgeUpdatedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(KnowledgeUpdatedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  }),
  KnowledgeDeletedEvent("KnowledgeDeletedInfraEvent", event -> {
    try {
      return Mapper.fromObjectToJsonString(KnowledgeDeletedInfraEvent.fromDomainToInfra(event));
    } catch (IOException e) {
      throw new RuntimeException("Error while parsing event payload: " + e.getMessage());
    }
  });

  public final String eventClass;
  public final Function<DomainEvent, String> serializer;

  SerializerMapper(String eventClass, Function<DomainEvent, String> serializer) {
    this.eventClass = eventClass;
    this.serializer = serializer;
  }

  public static String serialize(DomainEvent change) {
    Pattern pattern = Pattern.compile("(.+)Event");
    Matcher matcher = pattern.matcher(change.getClass().getSimpleName());
    if (matcher.find()) {
      return Arrays.stream(values())
              .filter(serializer -> serializer.eventClass.equals(matcher.group(1) + "InfraEvent"))
              .findAny()
              .orElseThrow(RuntimeException::new).serializer.apply(change);
    } else {
      throw new RuntimeException("No serializer for: " + change.getClass().getName());
    }
  }
}
