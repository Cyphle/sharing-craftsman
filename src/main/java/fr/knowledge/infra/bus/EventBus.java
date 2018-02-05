package fr.knowledge.infra.bus;

import fr.knowledge.domain.common.DomainEvent;

public interface EventBus {
  void apply(DomainEvent change);
  /*
  dispatch to query event handler (which will apply event to modify state)
   */
}
