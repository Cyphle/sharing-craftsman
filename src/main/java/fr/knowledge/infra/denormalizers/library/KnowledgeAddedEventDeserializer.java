package fr.knowledge.infra.denormalizers.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.events.library.KnowledgeAddedInfraEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class KnowledgeAddedEventDeserializer implements EventDeserializer {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public DomainEvent deserialize(String event) {
    try {
      return Mapper.fromJsonStringToObject(event, KnowledgeAddedInfraEvent.class).fromInfraToDomain();
    } catch (IOException e) {
      log.error("Error while initializing denormalization of category: " + this.getClass().getSimpleName() + ", " + e.getMessage());
      throw new RuntimeException("Error while parsing event payload: " + this.getClass().getSimpleName() + ", " + e.getMessage());
    }
  }

  @Override
  public String serialize(DomainEvent change) {
    try {
      return Mapper.fromObjectToJsonString(KnowledgeAddedInfraEvent.fromDomainToInfra(change));
    } catch (JsonProcessingException e) {
      log.error("Error while initializing normalization of category: " + this.getClass().getSimpleName() + ", " + e.getMessage());
      throw new RuntimeException("Error while normalizing event payload: " + this.getClass().getSimpleName() + ", " + e.getMessage());
    }
  }
}
