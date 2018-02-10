package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.UpdateCommentCommand;
import fr.knowledge.domain.comments.exceptions.CommentNotFoundException;
import fr.knowledge.domain.comments.exceptions.CommentException;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCommentCommandHandlerTest {
  @Mock
  private CommentRepository commentRepository;
  private UpdateCommentCommandHandler updateCommentCommandHandler;

  @Before
  public void setUp() {
    given(commentRepository.get(Id.of("aaa"))).willReturn(Optional.of(Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", "This is my content")));
    updateCommentCommandHandler = new UpdateCommentCommandHandler(commentRepository);
  }

  @Test
  public void should_update_comment() throws Exception {
    UpdateCommentCommand command = new UpdateCommentCommand("aaa", "john@doe.fr", "This is my updated comment");

    updateCommentCommandHandler.handle(command);

    Comment comment = Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", "This is my updated comment");
    comment.update(Username.from("john@doe.fr"), Content.of("This is my updated comment"));
    verify(commentRepository).save(comment);
  }

  @Test
  public void should_not_update_comment_if_commenter_is_not_right() throws Exception {
    UpdateCommentCommand command = new UpdateCommentCommand("aaa", "john@doe.fr", "This is my updated comment");

    try {
      updateCommentCommandHandler.handle(command);
    } catch (CommentException e) {
      assertThat(e.getMessage()).isEqualTo("Wrong commenter.");
    }
  }

  @Test(expected = CommentNotFoundException.class)
  public void should_throw_exception_if_comment_is_not_found() throws Exception {
    given(commentRepository.get(Id.of("bbb"))).willReturn(Optional.empty());
    UpdateCommentCommand command = new UpdateCommentCommand("bbb", "john@doe.fr", "This is my updated comment");

    updateCommentCommandHandler.handle(command);
  }

  @Test
  public void should_throw_invalid_comment_exception_if_content_of_comment_is_empty() throws Exception {
    UpdateCommentCommand command = new UpdateCommentCommand("aaa", "john@doe.fr", "");

    try {
      updateCommentCommandHandler.handle(command);
      fail("Should have throw comment exception");
    } catch (CommentException e) {
      assertThat(e.getMessage()).isEqualTo("Content cannot be empty.");
    }
  }
}
