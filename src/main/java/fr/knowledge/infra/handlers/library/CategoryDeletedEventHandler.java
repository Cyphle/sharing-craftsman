package fr.knowledge.infra.handlers.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;

public class CategoryDeletedEventHandler implements EventHandler {
  @Override
  public void apply(DomainEvent event) {
    throw new UnsupportedOperationException();
  }
}
