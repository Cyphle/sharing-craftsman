package fr.knowledge.infra.denormalizers.eventstore.comments;

import fr.knowledge.common.DateConverter;
import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.events.CommentDeletedEvent;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.infra.models.EventEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentDenormalizerTest {
  private CommentDenormalizer commentDenormalizer;

  @Before
  public void setUp() {
    commentDenormalizer = new CommentDenormalizer();
  }

  @Test
  public void should_rebuild_comment_state() {
    EventEntity commentAddedEvent = new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.comments.CommentAddedInfraEvent",
            "{\"id\":\"aaa\",\"commenter\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"caa\",\"content\":\"This is a bad comment\"}"
    );
    EventEntity commentUpdatedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.comments.CommentUpdatedInfraEvent",
            "{\"id\":\"caa\",\"content\":\"I am sorry. This is a good comment\"}"
    );
    EventEntity commentDeletedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 17, 50, 12)),
            "aaa",
            "fr.knowledge.infra.events.comments.CommentDeletedInfraEvent",
            "{\"id\":\"caa\"}"
    );

    Optional<Comment> denormalizedComment = commentDenormalizer.denormalize(Arrays.asList(commentAddedEvent, commentUpdatedEvent, commentDeletedEvent));

    Comment comment = Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "caa", "I am sorry. This is a good comment");
    comment.apply(new CommentDeletedEvent(Id.of("caa")));
    assertThat(denormalizedComment.get()).isEqualTo(comment);
  }
}
