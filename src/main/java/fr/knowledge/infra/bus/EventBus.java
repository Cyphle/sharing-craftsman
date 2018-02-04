package fr.knowledge.infra.bus;

import fr.knowledge.domain.common.DomainEvent;

public interface EventBus {
  void apply(DomainEvent change);
  /*
  Will be in charge to receive InfraEvents from adapters
  and save them in event store
  and dispatch to query event handler (which will apply event to modify state)
   */
}
