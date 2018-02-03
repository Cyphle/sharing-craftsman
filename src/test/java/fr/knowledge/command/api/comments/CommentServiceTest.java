package fr.knowledge.command.api.comments;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.comments.commands.AddCommentCommand;
import fr.knowledge.domain.comments.commands.DeleteCommentCommand;
import fr.knowledge.domain.comments.commands.UpdateCommentCommand;
import fr.knowledge.domain.common.valueobjects.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {
  private CommentService commentService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  @Mock
  private CommandBus commandBus;
  @Mock
  private AuthorizationService authorizationService;

  @Before
  public void setUp() throws Exception {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);
    given(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).willReturn(true);

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );
    commentService = new CommentService(authorizationService, commandBus);
  }

  @Test
  public void should_add_comment() throws Exception {
    ResponseEntity response = commentService.addComment(authorizationInfoDTO, CommentDTO.from("john@doe.fr", ContentType.CATEGORY.name(), "aaa", "My comment"), "john@doe.fr");

    AddCommentCommand command = new AddCommentCommand("john@doe.fr", ContentType.CATEGORY, "aaa", "My comment");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void should_update_comment() throws Exception {
    ResponseEntity response = commentService.updateComment(authorizationInfoDTO, CommentDTO.from("aaa", "john@doe.fr", "My comment"), "john@doe.fr");

    UpdateCommentCommand command = new UpdateCommentCommand("aaa", "john@doe.fr", "My comment");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void should_delete_comment() throws Exception {
    ResponseEntity response = commentService.deleteComment(authorizationInfoDTO, CommentDTO.from("aaa", "john@doe.fr"), "john@doe.fr");

    DeleteCommentCommand command = new DeleteCommentCommand("aaa", "john@doe.fr");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
