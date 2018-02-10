package fr.knowledge.infra.bus;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleEventBus implements EventBus {
  private final Map<Class<? extends DomainEvent>, EventHandler> handlers;

  public SimpleEventBus() {
    this.handlers = new HashMap<>();
  }

  @Override
  public void apply(DomainEvent event) {
    handlers.get(event.getClass()).apply(event);
  }

  @Override
  public void subscribe(Class<? extends DomainEvent> eventClass, EventHandler eventHandler) {
    handlers.put(eventClass, eventHandler);
  }

  @Override
  public Map<Class<? extends DomainEvent>, EventHandler> getHandlers() {
    return handlers;
  }
}
