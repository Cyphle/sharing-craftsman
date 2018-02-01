package fr.knowledge.command.api.favorites;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.api.library.CategoryDTO;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.favorites.commands.AddToMySelectionCommand;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
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
  public void setUp() throws Exception {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);

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
    ResponseEntity response = selectionService.addSelection(authorizationInfoDTO, SelectionDTO.from("john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa"));

    AddToMySelectionCommand command = new AddToMySelectionCommand("john@doe.fr", ContentType.KNOWLEDGE, "aaa");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
