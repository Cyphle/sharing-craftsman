package fr.knowledge.infra.handlers.comments;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.handlers.EventHandler;
import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;

public class CommentAddedEventHandler implements EventHandler {
  private ElasticSearchService elasticSearchService;

  public CommentAddedEventHandler(ElasticSearchService elasticSearchService) {
    this.elasticSearchService = elasticSearchService;
  }

  @Override
  public void apply(DomainEvent event) {
    CommentElastic commentElastic = CommentElastic.of(event.getAggregateId(), ((CommentAddedEvent) event).getCommenterContent(), ((CommentAddedEvent) event).getContentType().name(), ((CommentAddedEvent) event).getContentIdContent(), ((CommentAddedEvent) event).getContentContent());
    String element = Mapper.fromObjectToJsonString(commentElastic);
    elasticSearchService.createElement(ElasticIndexes.comments.name(), element);
  }
}
