package fr.knowledge.infra.denormalizers.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.models.EventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventMapper {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private Map<String, EventDeserializer> events;

  public EventMapper() {
    this.events = new HashMap<>();
    events.put("CategoryCreatedInfraEvent", new CategoryCreatedEventDeserializer());
    events.put("CategoryUpdatedInfraEvent", new CategoryUpdatedEventDeserializer());
    events.put("CategoryDeletedInfraEvent", new CategoryDeletedEventDeserializer());
    events.put("KnowledgeAddedInfraEvent", new KnowledgeAddedEventDeserializer());
    events.put("KnowledgeUpdatedInfraEvent", new KnowledgeUpdatedEventDeserializer());
    events.put("KnowledgeDeletedInfraEvent", new KnowledgeDeletedEventDeserializer());
  }

  public DomainEvent convertToDomain(EventEntity event) {
    Pattern pattern = Pattern.compile("events\\..+\\.(.+Event)");
    Matcher matcher = pattern.matcher(event.getPayloadType());
    if (matcher.find()) {
      return events.get(matcher.group(1))
              .deserialize(event.getPayload());
    } else {
      log.error("Error while initializing denormalization of category: " + this.getClass().getSimpleName());
      throw new RuntimeException("Error while parsing event payload: " + this.getClass().getSimpleName());
    }
  }

  public String serialize(DomainEvent change) {
    Pattern pattern = Pattern.compile("(.+)Event");
    Matcher matcher = pattern.matcher(change.getClass().getSimpleName());
    if (matcher.find()) {
      return events.get(matcher.group(1) + "InfraEvent")
              .serialize(change);
    } else {
      log.error("Error while initializing denormalization of category: " + this.getClass().getSimpleName());
      throw new RuntimeException("Error while parsing event payload: " + this.getClass().getSimpleName());
    }
  }
}
