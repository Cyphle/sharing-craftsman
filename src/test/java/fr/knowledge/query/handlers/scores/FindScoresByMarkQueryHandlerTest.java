package fr.knowledge.query.handlers.scores;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.infra.models.scores.ScoreElastic;
import fr.knowledge.query.queries.scores.FindScoresByMarkQuery;
import fr.knowledge.query.queries.scores.FindScoresForContentQuery;
import fr.knowledge.query.services.ScoreQueryService;
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
public class FindScoresByMarkQueryHandlerTest {
  private FindScoresByMarkQueryHandler findScoresByMarkQueryHandler;
  @Mock
  private ScoreQueryService scoreQueryService;

  @Before
  public void setUp() {
    given(scoreQueryService.findScoresWithMarkOf(5)).willReturn(Arrays.asList(
            ScoreElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", Mark.FIVE.value),
            ScoreElastic.of("sbb", "foo@bar.fr", ContentType.CATEGORY.name(), "caa", Mark.FIVE.value)
    ));
    findScoresByMarkQueryHandler = new FindScoresByMarkQueryHandler(scoreQueryService);
  }

  @Test
  public void should_find_scores_for_content() {
    FindScoresByMarkQuery query = new FindScoresByMarkQuery(5);

    List<ScoreElastic> scores = findScoresByMarkQueryHandler.handle(query);
    assertThat(scores).containsExactly(
            ScoreElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", Mark.FIVE.value),
            ScoreElastic.of("sbb", "foo@bar.fr", ContentType.CATEGORY.name(), "caa", Mark.FIVE.value)
    );
  }
}
