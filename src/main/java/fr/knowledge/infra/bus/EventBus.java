package fr.knowledge.infra.bus;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;

import java.util.Map;

public interface EventBus {
  void apply(DomainEvent event);

  void subscribe(Class<? extends DomainEvent> eventClass, EventHandler eventHandler);

  Map<Class<? extends DomainEvent>, EventHandler> getHandlers();
}
