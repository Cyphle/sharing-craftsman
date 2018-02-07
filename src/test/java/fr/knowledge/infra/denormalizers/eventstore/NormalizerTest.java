package fr.knowledge.infra.denormalizers.eventstore;

import fr.knowledge.common.DateConverter;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.library.events.CategoryUpdatedEvent;
import fr.knowledge.domain.library.valueobjects.Name;
import fr.knowledge.infra.models.EventEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class NormalizerTest {
  private Normalizer normalizer;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private EventSourcingConfig eventSourcingConfig;
  @Mock
  private DateService dateTimeService;

  @Before
  public void setUp() {
    given(idGenerator.generate()).willReturn("eaa");
    given(eventSourcingConfig.getVersion()).willReturn(1);
    given(dateTimeService.nowInDate()).willReturn(DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)));

    normalizer = new Normalizer(idGenerator, eventSourcingConfig, dateTimeService);
  }

  @Test
  public void should_normalize_a_category_domain_event_to_infra_event() {
    CategoryUpdatedEvent categoryUpdatedEvent = new CategoryUpdatedEvent(Id.of("aaa"), Name.of("SOLID"));

    EventEntity event = normalizer.normalize(categoryUpdatedEvent);

    assertThat(event).isEqualTo(new EventEntity(
            "eaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.library.CategoryUpdatedInfraEvent",
            "{\"id\":\"aaa\",\"name\":\"SOLID\"}"
    ));
  }

  @Test
  public void should_normalize_a_comment_domain_event_to_infra_event() {
    CommentAddedEvent commentAddedEvent = new CommentAddedEvent(Id.of("aaa"), Username.from("john@doe.fr"), ContentType.CATEGORY, Id.of("caa"), Content.of("This is my comment"));

    EventEntity event = normalizer.normalize(commentAddedEvent);

    assertThat(event).isEqualTo(new EventEntity(
            "eaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.comments.CommentAddedInfraEvent",
            "{\"id\":\"aaa\",\"commenter\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"caa\",\"content\":\"This is my comment\"}"
    ));
  }
}
