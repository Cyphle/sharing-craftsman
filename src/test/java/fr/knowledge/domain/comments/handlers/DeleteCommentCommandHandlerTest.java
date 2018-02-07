package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.DeleteCommentCommand;
import fr.knowledge.domain.comments.ports.CommentRepository;
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
public class DeleteCommentCommandHandlerTest {
  @Mock
  private CommentRepository commentRepository;
  private DeleteCommentCommandHandler deleteCommentCommandHandler;

  @Before
  public void setUp() {
    given(commentRepository.get(Id.of("aaa"))).willReturn(Optional.of(Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", "This is my content")));
    deleteCommentCommandHandler = new DeleteCommentCommandHandler(commentRepository);
  }

  @Test
  public void should_delete_comment() throws Exception {
    DeleteCommentCommand command = new DeleteCommentCommand("aaa", "john@doe.fr");

    deleteCommentCommandHandler.handle(command);

    Comment comment = Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", "This is my content");
    comment.delete();
    verify(commentRepository).save(comment);
  }
}
