package fr.knowledge.infra.adapters.favorites;

import fr.knowledge.common.DateConverter;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;
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
public class SelectionAdapterTest {
  @Mock
  private EventStore eventStore;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private EventSourcingConfig eventSourcingConfig;
  @Mock
  private DateService dateTimeService;
  private Normalizer normalizer;
  private SelectionAdapter selectionAdapter;
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
                    "fr.knowledge.infra.events.favorites.SelectionAddedInfraEvent",
                    "{\"id\":\"saa\",\"username\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"aaa\"}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "saa",
                    "fr.knowledge.infra.events.favorites.SelectionRemovedInfraEvent",
                    "{\"id\":\"saa\"}"
            )
    ));
    given(idGenerator.generate()).willReturn("aaa", "aab");
    given(eventSourcingConfig.getVersion()).willReturn(1);
    given(dateTimeService.nowInDate()).willReturn(
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12))
    );

    normalizer = new Normalizer(idGenerator, eventSourcingConfig, dateTimeService);

    selectionAdapter = new SelectionAdapter(eventBus, eventStore, normalizer);
  }

  @Test
  public void should_get_one_score_by_id() {
    Optional<Selection> fetchScore = selectionAdapter.get(Id.of("aaa"));

    Selection selection = Selection.of("saa", "john@doe.fr", ContentType.CATEGORY, "aaa");
    selection.apply(new SelectionRemovedEvent(Id.of("saa")));
    assertThat(fetchScore.get()).isEqualTo(selection);
  }

  @Test
  public void should_get_all_categories() {
    given(eventStore.findAll()).willReturn(Arrays.asList(
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "saa",
                    "fr.knowledge.infra.events.favorites.SelectionAddedInfraEvent",
                    "{\"id\":\"saa\",\"username\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"aaa\"}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "saa",
                    "fr.knowledge.infra.events.favorites.SelectionRemovedInfraEvent",
                    "{\"id\":\"saa\"}"
            ),
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "sbb",
                    "fr.knowledge.infra.events.favorites.SelectionAddedInfraEvent",
                    "{\"id\":\"sbb\",\"username\":\"john@doe.fr\",\"contentType\":\"KNOWLEDGE\",\"contentId\":\"bbb\"}"
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

    List<Selection> fetchSelection = selectionAdapter.getAll();

    Selection deletedSelection = Selection.of("saa", "john@doe.fr", ContentType.CATEGORY, "aaa");
    deletedSelection.apply(new SelectionRemovedEvent(Id.of("saa")));
    assertThat(fetchSelection).containsExactly(
            deletedSelection,
            Selection.of("sbb", "john@doe.fr", ContentType.KNOWLEDGE, "bbb")
    );
  }

  @Test
  public void should_save_score_changes() throws Exception {
    Selection selection = Selection.newSelection("saa", "john@doe.fr", ContentType.KNOWLEDGE, "caa");
    selection.delete(Username.from("john@doe.fr"));

    selectionAdapter.save(selection);

    verify(eventStore).save(new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "saa",
            "fr.knowledge.infra.events.favorites.SelectionAddedInfraEvent",
            "{\"id\":\"saa\",\"username\":\"john@doe.fr\",\"contentType\":\"KNOWLEDGE\",\"contentId\":\"caa\"}"
    ));
    verify(eventStore).save(new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "saa",
            "fr.knowledge.infra.events.favorites.SelectionRemovedInfraEvent",
            "{\"id\":\"saa\"}"
    ));
  }
}
