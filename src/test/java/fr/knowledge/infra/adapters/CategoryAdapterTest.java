/*
package fr.knowledge.infra.adapters;

import fr.knowledge.common.DateConverter;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.denormalizers.library.CategoryDenormalizer;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.domain.library.events.CategoryUpdatedEvent;
import fr.knowledge.domain.library.valueobjects.Name;
import fr.knowledge.infra.adapters.library.CategoryAdapter;
import fr.knowledge.infra.bus.EventBus;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryAdapterTest {
  private CategoryAdapter categoryAdapter;
  @Mock
  private EventBus eventBus;
  @Mock
  private EventStore eventStore;
  private CategoryDenormalizer categoryDenormalizer;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private EventSourcingConfig eventSourcingConfig;
  @Mock
  private DateService dateTimeService;

  @Before
  public void setUp() throws Exception {
    given(eventStore.findByAggregateId("aaa")).willReturn(Arrays.asList(
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "aaa",
                    "fr.knowledge.domain.library.events.CategoryCreatedEvent",
                    "{\"id\":{\"id\":\"aaa\"},\"name\":{\"name\":\"Architecture\"}}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "aaa",
                    "fr.knowledge.domain.library.events.CategoryUpdatedEvent",
                    "{\"id\":{\"id\":\"aaa\"},\"newName\":{\"name\":\"SOLID\"}}"
            )
    ));
    given(eventStore.findByAggregateId("bbb")).willReturn(Collections.singletonList(
            new EventEntity(
                    "aac",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 10, 15, 50, 12)),
                    "bbb",
                    "fr.knowledge.domain.library.events.CategoryCreatedEvent",
                    "{\"id\":{\"id\":\"bbb\"},\"name\":{\"name\":\"Four rules of simple design\"}}"
            )
    ));
    given(eventStore.findByAggregateId("ccc")).willReturn(Arrays.asList(
            new EventEntity(
                    "aad",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 8, 15, 50, 12)),
                    "ccc",
                    "fr.knowledge.domain.library.events.CategoryCreatedEvent",
                    "{\"id\":{\"id\":\"ccc\"},\"name\":{\"name\":\"Toto\"}}"
            ),
            new EventEntity(
                    "aae",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.MARCH, 9, 16, 50, 12)),
                    "ccc",
                    "fr.knowledge.domain.library.events.CategoryDeletedEvent",
                    "{\"id\":{\"id\":\"ccc\"}}"
            )
    ));
    given(idGenerator.generate()).willReturn("aaa", "aab");
    given(eventSourcingConfig.getVersion()).willReturn(1);
    given(dateTimeService.nowInDate()).willReturn(
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12))
    );

    categoryDenormalizer = new CategoryDenormalizer();
    categoryAdapter = new CategoryAdapter(eventBus, eventStore, categoryDenormalizer, idGenerator, eventSourcingConfig, dateTimeService);
  }

  @Test
  public void should_get_one_category_by_id() throws Exception {
    Optional<Category> fetchCategory = categoryAdapter.get(Id.of("aaa"));

    Category category = Category.of("aaa", "SOLID");
    assertThat(fetchCategory.get()).isEqualTo(category);
  }

  @Test
  public void should_get_all_categories() throws Exception {
    given(eventStore.findAll()).willReturn(Arrays.asList(
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "aaa",
                    "fr.knowledge.domain.library.events.CategoryCreatedEvent",
                    "{\"id\":{\"id\":\"aaa\"},\"name\":{\"name\":\"Architecture\"}}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "aaa",
                    "fr.knowledge.domain.library.events.CategoryUpdatedEvent",
                    "{\"id\":{\"id\":\"aaa\"},\"newName\":{\"name\":\"SOLID\"}}"
            ),
            new EventEntity(
                    "aac",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 10, 15, 50, 12)),
                    "bbb",
                    "fr.knowledge.domain.library.events.CategoryCreatedEvent",
                    "{\"id\":{\"id\":\"aaa\"},\"name\":{\"name\":\"Four rules of simple design\"}}"
            ),
            new EventEntity(
                    "aad",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 8, 15, 50, 12)),
                    "ccc",
                    "fr.knowledge.domain.library.events.CategoryCreatedEvent",
                    "{\"id\":{\"id\":\"aaa\"},\"name\":{\"name\":\"Toto\"}}"
            ),
            new EventEntity(
                    "aae",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.MARCH, 9, 16, 50, 12)),
                    "ccc",
                    "fr.knowledge.domain.library.events.CategoryDeletedEvent",
                    "{\"id\":{\"id\":\"ccc\"}}"
            ),
            new EventEntity(
                    "aaf",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.MARCH, 9, 16, 50, 12)),
                    "aaa",
                    "fr.knowledge.domain.library.events.KnowledgeAddedEvent",
                    "{\"id\":{\"id\":\"ccc\"}}"
            )
    ));

    List<Category> fetchCategories = categoryAdapter.getAll();

    assertThat(fetchCategories).containsExactly(
            Category.of("aaa", "SOLID"),
            Category.of("bbb", "Four rules of simple design")
    );
  }

  @Test
  public void should_save_category_changes() throws Exception {
    Category category = Category.newCategory("aaa", "Architecture");
    category.apply(new CategoryUpdatedEvent(Id.of("aaa"), Name.of("SOLID")));

    categoryAdapter.save(category);

    verify(eventStore).save(new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.domain.library.events.CategoryCreatedEvent",
            "{\"id\":{\"id\":\"aaa\"},\"name\":{\"name\":\"Architecture\"}}"
    ));
    verify(eventStore).save(new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "aaa",
            "fr.knowledge.domain.library.events.CategoryUpdatedEvent",
            "{\"id\":{\"id\":\"aaa\"},\"newName\":{\"name\":\"SOLID\"}}"
    ));
  }
}
*/
