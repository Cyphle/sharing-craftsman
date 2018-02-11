package fr.knowledge.query.handlers.comments;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.query.queries.comments.FindOneCommentQuery;
import fr.knowledge.query.services.CommentQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FindOneCommentQueryHandlerTest {
  private FindOneCommentQueryHandler findOneCommentQueryHandler;
  @Mock
  private CommentQueryService commentQueryService;

  @Before
  public void setUp() {
    given(commentQueryService.findOneById("caa")).willReturn(Collections.singletonList(CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "This is my comment")));
    findOneCommentQueryHandler = new FindOneCommentQueryHandler(commentQueryService);
  }

  @Test
  public void should_find_one_comment_by_id() {
    FindOneCommentQuery query = new FindOneCommentQuery("caa");

    List<CommentElastic> comments = findOneCommentQueryHandler.handle(query);
    assertThat(comments).containsExactly(CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "This is my comment"));
  }
}
