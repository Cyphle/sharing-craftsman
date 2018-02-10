package fr.knowledge.infra.handlers.comments;

import fr.knowledge.domain.comments.events.CommentUpdatedEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommentUpdatedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private CommentUpdatedEventHandler commentUpdatedEventHandler;

  @Before
  public void setUp() {
    commentUpdatedEventHandler = new CommentUpdatedEventHandler(elasticSearchService);
  }

  @Test
  public void should_create_category_in_elastic_search() {
    CommentUpdatedEvent event = new CommentUpdatedEvent(Id.of("caa"), Content.of("Updated comment"));

    commentUpdatedEventHandler.apply(event);

    Map<String, String> updates = new HashMap<>();
    updates.put("content", "Updated comment");
    verify(elasticSearchService).updateElement(ElasticIndexes.comments.name(), "caa", updates);
  }
}
