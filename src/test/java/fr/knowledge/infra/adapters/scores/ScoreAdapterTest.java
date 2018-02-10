package fr.knowledge.infra.adapters.scores;

import fr.knowledge.common.DateConverter;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.events.ScoreDeletedEvent;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.infra.bus.EventBus;
import fr.knowledge.infra.denormalizers.Normalizer;
import fr.knowledge.infra.models.EventEntity;
import fr.knowledge.infra.repositories.EventStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScoreAdapterTest {
  private ScoreAdapter scoreAdapter;
  private Normalizer normalizer;
  @Mock
  private EventStore eventStore;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private EventSourcingConfig eventSourcingConfig;
  @Mock
  private DateService dateTimeService;
  @Mock
  private EventBus eventBus;

  @Before
  public void setUp() {
    given(eventStore.findByAggregateId("aaa")).willReturn(Arrays.asList(
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "saa",
                    "fr.knowledge.infra.events.scores.ScoreCreatedInfraEvent",
                    "{\"id\":\"saa\",\"giver\":\"john@doe.fr\",\"contentType\":\"KNOWLEDGE\",\"contentId\":\"aaa\",\"mark\":2}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "saa",
                    "fr.knowledge.infra.events.scores.ScoreUpdatedInfraEvent",
                    "{\"id\":\"saa\",\"mark\":4}"
            )
    ));
    given(idGenerator.generate()).willReturn("aaa", "aab");
    given(eventSourcingConfig.getVersion()).willReturn(1);
    given(dateTimeService.nowInDate()).willReturn(
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12))
    );

    normalizer = new Normalizer(idGenerator, eventSourcingConfig, dateTimeService);

    scoreAdapter = new ScoreAdapter(eventBus, eventStore, normalizer);
  }

  @Test
  public void should_get_one_score_by_id() {
    Optional<Score> fetchScore = scoreAdapter.get(Id.of("aaa"));

    Score score = Score.of("saa", "john@doe.fr", ContentType.KNOWLEDGE, "aaa", Mark.FOUR);
    assertThat(fetchScore.get()).isEqualTo(score);
  }

  @Test
  public void should_get_all_categories() {
    given(eventStore.findAll()).willReturn(Arrays.asList(
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "saa",
                    "fr.knowledge.infra.events.scores.ScoreCreatedInfraEvent",
                    "{\"id\":\"saa\",\"giver\":\"john@doe.fr\",\"contentType\":\"KNOWLEDGE\",\"contentId\":\"aaa\",\"mark\":2}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "saa",
                    "fr.knowledge.infra.events.scores.ScoreUpdatedInfraEvent",
                    "{\"id\":\"saa\",\"mark\":4}"
            ),
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "sbb",
                    "fr.knowledge.infra.events.scores.ScoreCreatedInfraEvent",
                    "{\"id\":\"sbb\",\"giver\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"bbb\",\"mark\":4}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "sbb",
                    "fr.knowledge.infra.events.scores.ScoreUpdatedInfraEvent",
                    "{\"id\":\"sbb\",\"mark\":5}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 17, 50, 12)),
                    "sbb",
                    "fr.knowledge.infra.events.scores.ScoreDeletedInfraEvent",
                    "{\"id\":\"sbb\"}"
            ),
            new EventEntity(
                    "aac",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 10, 15, 50, 12)),
                    "bbb",
                    "fr.knowledge.infra.events.library.CategoryCreatedInfraEvent",
                    "{\"id\":\"bbb\",\"name\":\"Four rules of simple design\"}"
            ),
            new EventEntity(
                    "aad",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 8, 15, 50, 12)),
                    "ccc",
                    "fr.knowledge.infra.events.library.CategoryCreatedInfraEvent",
                    "{\"id\":\"ccc\",\"name\":\"Toto\"}"
            ),
            new EventEntity(
                    "aae",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.MARCH, 9, 16, 50, 12)),
                    "ccc",
                    "fr.knowledge.infra.events.library.CategoryDeletedInfraEvent",
                    "{\"id\":\"ccc\"}"
            )
    ));

    List<Score> fetchScores = scoreAdapter.getAll();

    Score deletedScore = Score.of("sbb", "john@doe.fr", ContentType.CATEGORY, "bbb", Mark.FIVE);
    deletedScore.apply(new ScoreDeletedEvent(Id.of("sbb")));
    assertThat(fetchScores).containsExactly(
            Score.of("saa", "john@doe.fr", ContentType.KNOWLEDGE, "aaa", Mark.FOUR),
            deletedScore
    );
  }

  @Test
  public void should_save_score_changes() throws Exception {
    Score score = Score.newScore("aaa", "john@doe.fr", ContentType.KNOWLEDGE, "caa", Mark.ONE);
    score.update(Username.from("john@doe.fr"), Mark.THREE);

    scoreAdapter.save(score);

    verify(eventStore).save(new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.scores.ScoreCreatedInfraEvent",
            "{\"id\":\"aaa\",\"giver\":\"john@doe.fr\",\"contentType\":\"KNOWLEDGE\",\"contentId\":\"caa\",\"mark\":1}"
    ));
    verify(eventStore).save(new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.scores.ScoreUpdatedInfraEvent",
            "{\"id\":\"aaa\",\"mark\":3}"
    ));
  }
}
