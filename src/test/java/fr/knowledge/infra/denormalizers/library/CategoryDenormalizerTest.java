package fr.knowledge.infra.denormalizers.library;

import fr.knowledge.common.DateConverter;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.events.CategoryUpdatedEvent;
import fr.knowledge.domain.library.events.KnowledgeAddedEvent;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import fr.knowledge.domain.library.valueobjects.Name;
import fr.knowledge.infra.models.EventEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDenormalizerTest {
  private CategoryDenormalizer categoryDenormalizer;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private EventSourcingConfig eventSourcingConfig;
  @Mock
  private DateService dateTimeService;

  @Before
  public void setUp() throws Exception {
    given(idGenerator.generate()).willReturn("eaa");
    given(eventSourcingConfig.getVersion()).willReturn(1);
    given(dateTimeService.nowInDate()).willReturn(DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)));

    categoryDenormalizer = new CategoryDenormalizer(idGenerator, eventSourcingConfig, dateTimeService);
  }

  @Test
  public void should_rebuild_category_state_without_any_knowledge() throws Exception {
    EventEntity creationEvent = new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryCreatedInfraEvent",
            "{\"id\":\"aaa\",\"name\":\"Architecture\"}"
    );
    EventEntity updatedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryUpdatedInfraEvent",
            "{\"id\":\"aaa\",\"name\":\"SOLID\"}"
    );

    Optional<Category> denormalizedCategory = categoryDenormalizer.denormalize(Arrays.asList(updatedEvent, creationEvent));

    Category category = Category.of("aaa", "SOLID");
    assertThat(denormalizedCategory.get()).isEqualTo(category);
  }

  @Test
  public void should_return_optional_empty_if_category_has_been_deleted() throws Exception {
    EventEntity creationEvent = new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryCreatedInfraEvent",
            "{\"id\":\"aaa\",\"name\":\"Architecture\"}"
    );
    EventEntity updatedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryUpdatedInfraEvent",
            "{\"id\":\"aaa\",\"name\":\"SOLID\"}"
    );
    EventEntity deletedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.MARCH, 3, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryDeletedInfraEvent",
            "{\"id\":\"aaa\"}"
    );

    Optional<Category> denormalizedCategory = categoryDenormalizer.denormalize(Arrays.asList(updatedEvent, creationEvent, deletedEvent));

    assertThat(denormalizedCategory.get().isDeleted()).isTrue();
  }

  @Test
  public void should_rebuild_category_with_its_knowledge() {
    EventEntity creationEvent = new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryCreatedInfraEvent",
            "{\"id\":\"aaa\",\"name\":\"Architecture\"}"
    );
    EventEntity addKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 4, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.KnowledgeAddedInfraEvent",
            "{\"categoryId\":\"aaa\",\"knowledgeId\":\"kaa\",\"creator\":\"john@doe.fr\",\"title\":\"My knowledge title\",\"content\":\"My super knowledge content\"}"
    );
    EventEntity updateKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 5, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.KnowledgeUpdatedInfraEvent",
            "{\"categoryId\":\"aaa\",\"knowledgeId\":\"kaa\",\"creator\":\"john@doe.fr\",\"title\":\"My knowledge title\",\"content\":\"My super knowledge content updated\"}"
    );
    EventEntity secondAddKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 17, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.KnowledgeAddedInfraEvent",
            "{\"categoryId\":\"aaa\",\"knowledgeId\":\"kbb\",\"creator\":\"john@doe.fr\",\"title\":\"My knowledge\",\"content\":\"This is my second content\"}"
    );
    EventEntity deleteSecondKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 7, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.KnowledgeDeletedInfraEvent",
            "{\"categoryId\":\"aaa\",\"knowledgeId\":\"kbb\"}"
    );

    Optional<Category> denormalizedCategory = categoryDenormalizer.denormalize(Arrays.asList(creationEvent, addKnowledgeEvent, updateKnowledgeEvent, secondAddKnowledgeEvent, deleteSecondKnowledgeEvent));

    Category category = Category.of("aaa", "Architecture");
    category.apply(new KnowledgeAddedEvent(Id.of("aaa"), Knowledge.of("kaa", "john@doe.fr", "My knowledge title", "My super knowledge content updated")));
    category.resetChanges();
    assertThat(denormalizedCategory.get()).isEqualTo(category);
  }

  @Test
  public void should_normalize_a_domain_event_to_infra_event() throws Exception {
    CategoryUpdatedEvent categoryUpdatedEvent = new CategoryUpdatedEvent(Id.of("aaa"), Name.of("SOLID"));

    EventEntity event = categoryDenormalizer.normalize(categoryUpdatedEvent);

    assertThat(event).isEqualTo(new EventEntity(
            "eaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryUpdatedInfraEvent",
            "{\"id\":\"aaa\",\"name\":\"SOLID\"}"
    ));
  }
}
