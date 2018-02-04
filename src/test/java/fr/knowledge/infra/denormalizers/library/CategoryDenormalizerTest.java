package fr.knowledge.infra.denormalizers.library;

import fr.knowledge.common.DateConverter;
import fr.knowledge.domain.library.aggregates.Category;
import fr.knowledge.infra.events.library.CategoryDeletedInfraEvent;
import fr.knowledge.infra.events.library.CategoryUpdatedInfraEvent;
import fr.knowledge.infra.models.EventEntity;
import fr.knowledge.utils.Mapper;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryDenormalizerTest {
  private CategoryDenormalizer categoryDenormalizer;

  @Before
  public void setUp() throws Exception {
    categoryDenormalizer = new CategoryDenormalizer();
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

    assertThat(denormalizedCategory.isPresent()).isFalse();
  }

  /*


  @Test
  public void should_rebuild_category_with_its_knowledge() throws Exception {
    EventEntity creationEvent = new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.domain.library.events.CategoryCreatedEvent",
            "{\"id\":{\"id\":\"aaa\"},\"name\":{\"name\":\"Architecture\"}}"
    );
    EventEntity addKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 4, 16, 50, 12)),
            "aaa",
            "fr.knowledge.domain.library.events.KnowledgeAddedEvent",
            "{\"categoryId\":{\"id\":\"aaa\"},\"knowledge\":{\"id\":{\"id\":\"kaa\"},\"creator\":{\"username\":\"john@doe.fr\"},\"title\":{\"title\":\"My knowledge\"},\"content\":{\"content\":\"This is my content\"}}}"
    );
    EventEntity updateKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 5, 16, 50, 12)),
            "aaa",
            "fr.knowledge.domain.library.events.KnowledgeUpdatedEvent",
            "{\"categoryId\":{\"id\":\"aaa\"},\"knowledge\":{\"id\":{\"id\":\"kaa\"},\"creator\":{\"username\":\"john@doe.fr\"},\"title\":{\"title\":\"My knowledge\"},\"content\":{\"content\":\"This is my modified content\"}}}"
    );
    EventEntity secondAddKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 17, 50, 12)),
            "aaa",
            "fr.knowledge.domain.library.events.KnowledgeAddedEvent",
            "{\"categoryId\":{\"id\":\"aaa\"},\"knowledge\":{\"id\":{\"id\":\"kbb\"},\"creator\":{\"username\":\"john@doe.fr\"},\"title\":{\"title\":\"My knowledge\"},\"content\":{\"content\":\"This is my second content\"}}}"
    );
    EventEntity deleteSecondKnowledgeEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 7, 16, 50, 12)),
            "aaa",
            "fr.knowledge.domain.library.events.KnowledgeDeletedEvent",
            "{\"categoryId\":{\"id\":\"aaa\"},\"knowledgeId\":{\"id\":\"kbb\"}}"
    );

    Optional<Category> denormalizedCategory = categoryDenormalizer.denormalize(Arrays.asList(creationEvent, addKnowledgeEvent, updateKnowledgeEvent, secondAddKnowledgeEvent, deleteSecondKnowledgeEvent));

    Category category = Category.of("aaa", "Architecture");
    category.apply(new KnowledgeAddedEvent(Id.of("aaa"), Knowledge.of("kaa", "john@doe.fr", "My knowledge", "This is my modified content")));
    category.resetChanges();
    assertThat(denormalizedCategory.get()).isEqualTo(category);
  }
  */
}
