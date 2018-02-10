package fr.knowledge.infra.handlers.comments;

import fr.knowledge.common.Mapper;
import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.infra.repositories.ElasticIndexes;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommentAddedEventHandlerTest {
  @Mock
  private ElasticSearchService elasticSearchService;
  private CommentAddedEventHandler commentAddedEventHandler;

  @Before
  public void setUp() {
    commentAddedEventHandler = new CommentAddedEventHandler(elasticSearchService);
  }

  @Test
  public void should_create_category_in_elastic_search() {
    CommentAddedEvent event = new CommentAddedEvent(Id.of("caa"), Username.from("john@doe.fr"), ContentType.KNOWLEDGE, Id.of("kaa"), Content.of("This is a really good thing"));

    commentAddedEventHandler.apply(event);

    CommentElastic comment = CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "kaa", "This is a really good thing");
    verify(elasticSearchService).createElement(ElasticIndexes.comments.name(), Mapper.fromObjectToJsonString(comment));
  }
}
