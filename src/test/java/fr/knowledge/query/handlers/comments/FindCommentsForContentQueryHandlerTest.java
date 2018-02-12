package fr.knowledge.query.handlers.comments;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.query.queries.comments.FindCommentsForContentQuery;
import fr.knowledge.query.services.CommentQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FindCommentsForContentQueryHandlerTest {
  private FindCommentsForContentQueryHandler findCommentsForContentQueryHandler;
  @Mock
  private CommentQueryService commentQueryService;

  @Before
  public void setUp() {
    given(commentQueryService.findCommentsFor("aaa")).willReturn(Arrays.asList(
            CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "This is my comment"),
            CommentElastic.of("cbb", "foo@bar.fr", ContentType.KNOWLEDGE.name(), "aaa", "This is my another one")
    ));
    findCommentsForContentQueryHandler = new FindCommentsForContentQueryHandler(commentQueryService);
  }

  @Test
  public void should_find_comments_for_content() {
    FindCommentsForContentQuery query = new FindCommentsForContentQuery("aaa");

    List<CommentElastic> comments = findCommentsForContentQueryHandler.handle(query);
    assertThat(comments).containsExactly(
            CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "This is my comment"),
            CommentElastic.of("cbb", "foo@bar.fr", ContentType.KNOWLEDGE.name(), "aaa", "This is my another one")
    );
  }
}
