package fr.knowledge.infra.adapters.comments;

import fr.knowledge.common.DateConverter;
import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommentAdapterTest {
  @Mock
  private EventStore eventStore;
  @Mock
  private EventBus eventBus;
  private Normalizer normalizer;
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private EventSourcingConfig eventSourcingConfig;
  @Mock
  private DateService dateTimeService;
  private CommentAdapter commentAdapter;

  @Before
  public void setUp() {
    given(eventStore.findByAggregateId("aaa")).willReturn(Arrays.asList(
            new EventEntity(
                    "aaa",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
                    "aaa",
                    "fr.knowledge.infra.events.comments.CommentAddedInfraEvent",
                    "{\"id\":\"aaa\",\"commenter\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"caa\",\"content\":\"This is a bad comment\"}"
            ),
            new EventEntity(
                    "aab",
                    1,
                    DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
                    "aaa",
                    "fr.knowledge.infra.events.comments.CommentUpdatedInfraEvent",
                    "{\"id\":\"caa\",\"content\":\"This is my comment\"}"
            )
    ));
    given(idGenerator.generate()).willReturn("aaa", "aab");
    given(eventSourcingConfig.getVersion()).willReturn(1);
    given(dateTimeService.nowInDate()).willReturn(
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12))
    );

    normalizer = new Normalizer(idGenerator, eventSourcingConfig, dateTimeService);

    commentAdapter = new CommentAdapter(eventBus, eventStore, normalizer);
  }

  @Test
  public void should_get_one_comment_by_id() {
    Optional<Comment> fetchComment = commentAdapter.get(Id.of("aaa"));

    Comment comment = Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "caa", "This is my comment");
    assertThat(fetchComment.get()).isEqualTo(comment);
  }

  @Test
  public void should_save_category_changes() throws Exception {
    Comment comment = Comment.newComment("aaa", "john@doe.fr", ContentType.CATEGORY, "caa", "This is my content");
    comment.update(Username.from("john@doe.fr"), Content.of("Revised comment"));

    commentAdapter.save(comment);

    verify(eventStore).save(new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.comments.CommentAddedInfraEvent",
            "{\"id\":\"aaa\",\"commenter\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"caa\",\"content\":\"This is my content\"}"
    ));
    verify(eventStore).save(new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.comments.CommentUpdatedInfraEvent",
            "{\"id\":\"aaa\",\"content\":\"Revised comment\"}"
    ));
  }
}
