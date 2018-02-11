package fr.knowledge.query.api.favorites;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.infra.models.comments.CommentElastic;
import fr.knowledge.infra.models.favorites.SelectionElastic;
import fr.knowledge.query.api.comments.QueryCommentService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.comments.FindCommentsForContentQuery;
import fr.knowledge.query.queries.favorites.FindSelectionForUserQuery;
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
public class QuerySelectionServiceTest {
  private QuerySelectionService querySelectionService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  @Mock
  private QueryBus queryBus;
  @Mock
  private AuthorizationService authorizationService;

  @Before
  public void setUp() {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);
    given(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).willReturn(true);
    given(queryBus.send(new FindSelectionForUserQuery("john@doe.fr"))).willReturn(Collections.singletonList(
            SelectionElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa")
    ));

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );
    querySelectionService = new QuerySelectionService(authorizationService, queryBus);
  }

  @Test
  public void should_get_favorites_of_user() {
    ResponseEntity response = querySelectionService.getSelectionsOfUser(authorizationInfoDTO, "john@doe.fr");

    FindSelectionForUserQuery query = new FindSelectionForUserQuery("john@doe.fr");
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(Arrays.asList(
            SelectionElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa")
    ));
  }
}
