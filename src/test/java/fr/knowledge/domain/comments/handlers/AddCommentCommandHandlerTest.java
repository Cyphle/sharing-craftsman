package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.AddCommentCommand;
import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.common.valueobjects.Content;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddCommentCommandHandlerTest {
  @Mock
  private CommentRepository commentRepository;
  @Mock
  private IdGenerator idGenerator;
  private AddCommentCommandHandler addCommentCommandHandler;

  @Before
  public void setUp() throws Exception {
    given(idGenerator.generate()).willReturn("aaa");
    addCommentCommandHandler = new AddCommentCommandHandler(idGenerator, commentRepository);
  }

  @Test
  public void should_add_comment_to_content() throws Exception {
    AddCommentCommand command = new AddCommentCommand("john@doe.fr", ContentType.CATEGORY, "aaa", "This is my comment");

    addCommentCommandHandler.handle(command);

    Comment comment = Comment.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", "This is my comment");
    comment.saveChanges(new CommentAddedEvent(Id.of("aaa"), Username.from("john@doe.fr"), ContentType.CATEGORY, Id.of("aaa"), Content.of("This is my comment")));
    verify(commentRepository).save(comment);
  }
}
