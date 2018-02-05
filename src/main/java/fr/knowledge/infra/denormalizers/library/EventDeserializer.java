package fr.knowledge.infra.denormalizers.library;

import fr.knowledge.domain.common.DomainEvent;

public interface EventDeserializer {
  DomainEvent deserialize(String event);

  String serialize(DomainEvent change);
}
