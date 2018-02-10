package fr.knowledge.infra.handlers;

import fr.knowledge.domain.common.DomainEvent;

public interface EventHandler {
  void apply(DomainEvent event);
}
