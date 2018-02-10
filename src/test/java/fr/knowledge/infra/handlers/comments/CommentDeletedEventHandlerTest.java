package fr.knowledge.infra.handlers.comments;

import fr.knowledge.domain.comments.events.CommentDeletedEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryDeletedEvent;
import fr.knowledge.infra.handlers.library.CategoryDeletedEventHandler;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommentDeletedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private CommentDeletedEventHandler commentDeletedEventHandler;

  @Before
  public void setUp() {
    commentDeletedEventHandler = new CommentDeletedEventHandler(elasticSearchService);
  }

  @Test
  public void should_delete_category() {
    CommentDeletedEvent event = new CommentDeletedEvent(Id.of("aaa"));

    commentDeletedEventHandler.apply(event);

    verify(elasticSearchService).deleteElement(ElasticIndexes.comments.name(), "aaa");
  }
}
