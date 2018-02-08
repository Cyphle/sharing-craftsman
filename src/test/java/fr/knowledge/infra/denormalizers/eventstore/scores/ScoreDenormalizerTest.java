package fr.knowledge.infra.denormalizers.eventstore.scores;

import fr.knowledge.common.DateConverter;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.events.ScoreDeletedEvent;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.infra.models.EventEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreDenormalizerTest {
  private ScoreDenormalizer scoreDenormalizer;

  @Before
  public void setUp() {
    scoreDenormalizer = new ScoreDenormalizer();
  }

  @Test
  public void should_rebuild_score_state() {
    EventEntity scoreCreatedEvent = new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "saa",
            "fr.knowledge.infra.events.scores.ScoreCreatedInfraEvent",
            "{\"id\":\"saa\",\"giver\":\"john@doe.fr\",\"contentType\":\"KNOWLEDGE\",\"contentId\":\"aaa\",\"mark\":2}"
    );
    EventEntity scoreUpdatedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "saa",
            "fr.knowledge.infra.events.scores.ScoreUpdatedInfraEvent",
            "{\"id\":\"saa\",\"mark\":4}"
    );
    EventEntity scoreDeletedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 17, 50, 12)),
            "saa",
            "fr.knowledge.infra.events.scores.ScoreDeletedInfraEvent",
            "{\"id\":\"saa\"}"
    );

    Optional<Score> denormalizedScore = scoreDenormalizer.denormalize(Arrays.asList(scoreCreatedEvent, scoreUpdatedEvent, scoreDeletedEvent));

    Score score = Score.of("saa", "john@doe.fr", ContentType.KNOWLEDGE, "aaa", Mark.FOUR);
    score.apply(new ScoreDeletedEvent(Id.of("saa")));
    assertThat(denormalizedScore.get()).isEqualTo(score);
  }
}
