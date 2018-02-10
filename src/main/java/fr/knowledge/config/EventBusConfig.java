package fr.knowledge.config;

import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.comments.events.CommentDeletedEvent;
import fr.knowledge.domain.comments.events.CommentUpdatedEvent;
import fr.knowledge.domain.favorites.events.SelectionAddedEvent;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;
import fr.knowledge.domain.library.events.*;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.domain.scores.events.ScoreDeletedEvent;
import fr.knowledge.domain.scores.events.ScoreUpdatedEvent;
import fr.knowledge.infra.bus.EventBus;
import fr.knowledge.infra.handlers.comments.CommentAddedEventHandler;
import fr.knowledge.infra.handlers.comments.CommentDeletedEventHandler;
import fr.knowledge.infra.handlers.comments.CommentUpdatedEventHandler;
import fr.knowledge.infra.handlers.favorites.SelectionAddedEventHandler;
import fr.knowledge.infra.handlers.favorites.SelectionRemovedEventHandler;
import fr.knowledge.infra.handlers.library.*;
import fr.knowledge.infra.handlers.scores.ScoreCreatedEventHandler;
import fr.knowledge.infra.handlers.scores.ScoreDeletedEventHandler;
import fr.knowledge.infra.handlers.scores.ScoreUpdatedEventHandler;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventBusConfig {
  private EventBus eventBus;
  private ElasticSearchService elasticSearchService;

  @Autowired
  public EventBusConfig(EventBus eventBus, ElasticSearchService elasticSearchService) {
    this.eventBus = eventBus;
    this.elasticSearchService = elasticSearchService;
  }

  public void configure() {
    // Comments
    eventBus.subscribe(CommentAddedEvent.class, new CommentAddedEventHandler(elasticSearchService));
    eventBus.subscribe(CommentUpdatedEvent.class, new CommentUpdatedEventHandler(elasticSearchService));
    eventBus.subscribe(CommentDeletedEvent.class, new CommentDeletedEventHandler(elasticSearchService));
    // Favorites
    eventBus.subscribe(SelectionAddedEvent.class, new SelectionAddedEventHandler(elasticSearchService));
    eventBus.subscribe(SelectionRemovedEvent.class, new SelectionRemovedEventHandler(elasticSearchService));
    // Library
    eventBus.subscribe(CategoryCreatedEvent.class, new CategoryCreatedEventHandler(elasticSearchService));
    eventBus.subscribe(CategoryUpdatedEvent.class, new CategoryUpdatedEventHandler(elasticSearchService));
    eventBus.subscribe(CategoryDeletedEvent.class, new CategoryDeletedEventHandler(elasticSearchService));
    eventBus.subscribe(KnowledgeAddedEvent.class, new KnowledgeAddedEventHandler(elasticSearchService));
    eventBus.subscribe(KnowledgeUpdatedEvent.class, new KnowledgeUpdatedEventHandler(elasticSearchService));
    eventBus.subscribe(KnowledgeDeletedEvent.class, new KnowledgeDeletedEventHandler(elasticSearchService));
    // Scores
    eventBus.subscribe(ScoreCreatedEvent.class, new ScoreCreatedEventHandler(elasticSearchService));
    eventBus.subscribe(ScoreUpdatedEvent.class, new ScoreUpdatedEventHandler(elasticSearchService));
    eventBus.subscribe(ScoreDeletedEvent.class, new ScoreDeletedEventHandler(elasticSearchService));
  }
  /*
  WIll subscribe all event handlers
   */
}
