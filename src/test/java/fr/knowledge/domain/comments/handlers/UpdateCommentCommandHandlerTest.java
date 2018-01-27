package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.UpdateCommentCommand;
import fr.knowledge.domain.comments.events.CommentUpdatedEvent;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCommentCommandHandlerTest {
  @Mock
  private CommentRepository commentRepository;
  private UpdateCommentCommandHandler updateCommentCommandHandler;

  @Before
  public void setUp() throws Exception {
    given(commentRepository.get(Id.of("aaa"))).willReturn(Optional.of(Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", "This is my content")));
    updateCommentCommandHandler = new UpdateCommentCommandHandler(commentRepository);
  }

  @Test
  public void should_update_comment() throws Exception {
    UpdateCommentCommand command = new UpdateCommentCommand("aaa", "This is my updated comment");

    updateCommentCommandHandler.handle(command);

    Comment comment = Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", "This is my updated comment");
    comment.saveChanges(new CommentUpdatedEvent(Id.of("aaa"), Content.of("This is my updated comment")));
    verify(commentRepository).save(comment);
  }
}
