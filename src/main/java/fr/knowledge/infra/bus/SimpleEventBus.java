package fr.knowledge.infra.bus;

import fr.knowledge.domain.common.DomainEvent;
import org.springframework.stereotype.Component;

@Component
public class SimpleEventBus implements EventBus {
  @Override
  public void apply(DomainEvent change) {
    throw new UnsupportedOperationException();
  }
}
