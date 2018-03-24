package fr.knowledge.query.api.comments;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.comments.FindCommentsForContentQuery;
import fr.knowledge.query.queries.library.FindOneCategoryQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueryCommentServiceTest {
  private QueryCommentService queryCommentService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  @Mock
  private QueryBus queryBus;
  @Mock
  private AuthorizationService authorizationService;

  @Before
  public void setUp() {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);
    given(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).willReturn(true);
    given(queryBus.send(new FindCommentsForContentQuery("aaa"))).willReturn(Arrays.asList(
            CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "My comment"),
            CommentElastic.of("cbb", "john@doe.fr", ContentType.CATEGORY.name(), "bbb", "My other comment")
    ));
    given(queryBus.send(new FindOneCategoryQuery("caa"))).willReturn(Collections.singletonList(
            CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "My comment")
    ));

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );
    queryCommentService = new QueryCommentService(authorizationService, queryBus);
  }

  @Test
  public void should_get_comments_of_content() {
    ResponseEntity response = queryCommentService.getCommentsForContent("aaa");

    FindCommentsForContentQuery query = new FindCommentsForContentQuery("aaa");
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(Arrays.asList(
            CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "My comment"),
            CommentElastic.of("cbb", "john@doe.fr", ContentType.CATEGORY.name(), "bbb", "My other comment")
    ));
  }

  @Test
  public void should_get_comment_by_id() {
    ResponseEntity response = queryCommentService.getCommentById(authorizationInfoDTO, "caa");

    FindOneCategoryQuery query = new FindOneCategoryQuery("caa");
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "My comment"));
  }
}
