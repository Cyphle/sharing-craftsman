package fr.knowledge.query.api.scores;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.infra.models.scores.ScoreElastic;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.scores.FindScoresByMarkQuery;
import fr.knowledge.query.queries.scores.FindScoresForContentQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueryScoreServiceTest {
  @Mock
  private QueryBus queryBus;
  @Mock
  private AuthorizationService authorizationService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  private QueryScoreService queryScoreService;

  @Before
  public void setUp() {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);
    given(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).willReturn(true);

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );

    queryScoreService = new QueryScoreService(authorizationService, queryBus);
  }

  @Test
  public void should_get_score_by_content_id() {
    given(queryBus.send(new FindScoresForContentQuery("aaa"))).willReturn(Collections.singletonList(
            ScoreElastic.of("saa", "john@doe.fr", ContentType.CATEGORY.name(), "aaa", Mark.FOUR.value)
    ));

    ResponseEntity response = queryScoreService.getScoresByContentId("aaa");

    FindScoresForContentQuery query = new FindScoresForContentQuery("aaa");
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(Collections.singletonList(ScoreElastic.of("saa", "john@doe.fr", ContentType.CATEGORY.name(), "aaa", Mark.FOUR.value)));
  }

  @Test
  public void should_get_scores_for_given_mark() {
    given(queryBus.send(new FindScoresByMarkQuery(3))).willReturn(Collections.singletonList(
            ScoreElastic.of("saa", "john@doe.fr", ContentType.CATEGORY.name(), "aaa", Mark.THREE.value)
    ));

    ResponseEntity response = queryScoreService.getScoresByMark(authorizationInfoDTO, 3);

    FindScoresByMarkQuery query = new FindScoresByMarkQuery(3);
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(Collections.singletonList(ScoreElastic.of("saa", "john@doe.fr", ContentType.CATEGORY.name(), "aaa", Mark.THREE.value)));
  }
}
