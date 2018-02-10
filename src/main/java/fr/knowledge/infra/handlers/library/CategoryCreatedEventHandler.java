package fr.knowledge.infra.handlers.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;

public class CategoryCreatedEventHandler implements EventHandler {
  @Override
  public void apply(DomainEvent event) {
    throw new UnsupportedOperationException();

    /*
      - Create category in elastic search
      - ElasticCategory (containing ElasticKnowledge)
     */
  }

  /*
  dispatch to query event handler (which will apply event to modify state)
   */
    /*
      - Dispatch to event handlers
      - Event handlers update elements in elasticsearch
     */
}
