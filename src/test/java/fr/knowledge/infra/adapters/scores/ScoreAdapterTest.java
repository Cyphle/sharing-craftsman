package fr.knowledge.infra.adapters.scores;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.valueobjects.Mark;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreAdapterTest {
  private ScoreAdapter scoreAdapter;

  @Test
  public void should_get_one_comment_by_id() {
    Optional<Score> fetchScore = scoreAdapter.get(Id.of("aaa"));

    Score score = Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "caa", Mark.FOUR);
    assertThat(fetchScore.get()).isEqualTo(score);
  }
}
