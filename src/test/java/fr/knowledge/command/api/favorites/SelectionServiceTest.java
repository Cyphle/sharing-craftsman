package fr.knowledge.command.api.favorites;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.favorites.commands.AddToMySelectionCommand;
import fr.knowledge.domain.favorites.commands.RemoveFromMySelectionCommand;
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
public class SelectionServiceTest {
  private SelectionService selectionService;
  @Mock
  private AuthorizationService authorizationService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  @Mock
  private CommandBus commandBus;

  @Before
  public void setUp() {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);
    given(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).willReturn(true);

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );
    selectionService = new SelectionService(authorizationService, commandBus);
  }

  @Test
  public void should_add_to_my_selection() throws Exception {
    ResponseEntity response = selectionService.addSelection(authorizationInfoDTO, SelectionDTO.from("john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa"), "john@doe.fr");

    AddToMySelectionCommand command = new AddToMySelectionCommand("john@doe.fr", ContentType.KNOWLEDGE, "aaa");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void should_remove_from_my_selection() throws Exception {
    ResponseEntity response = selectionService.removeSelection(authorizationInfoDTO, SelectionDTO.from("aaa", "john@doe.fr"), "john@doe.fr");

    RemoveFromMySelectionCommand command = new RemoveFromMySelectionCommand("aaa", "john@doe.fr");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
