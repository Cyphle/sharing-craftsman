package fr.knowledge.command.api.library;

import com.google.common.collect.Sets;
import fr.knowledge.command.api.common.*;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.domain.library.commands.DeleteCategoryCommand;
import fr.knowledge.domain.library.commands.UpdateCategoryCommand;
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
public class LibraryServiceTest {
  @Mock
  private CommandBus commandBus;
  private LibraryService libraryService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  @Mock
  private AuthorizationService authorizationServie;

  @Before
  public void setUp() throws Exception {
    given(authorizationServie.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );
    libraryService = new LibraryService(authorizationServie, commandBus);
  }

  @Test
  public void should_create_category() throws Exception {
    ResponseEntity response = libraryService.createCategory(authorizationInfoDTO, CategoryDTO.from("Architecture"));

    CreateCategoryCommand command = new CreateCategoryCommand("Architecture");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void should_update_category() throws Exception {
    ResponseEntity response = libraryService.updateCategory(authorizationInfoDTO, CategoryDTO.from("aaa", "Architecture"));

    UpdateCategoryCommand command = new UpdateCategoryCommand("aaa", "Architecture");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void should_delete_category() throws Exception {
    ResponseEntity response = libraryService.deleteCategory(authorizationInfoDTO, CategoryDTO.fromId("aaa"));

    DeleteCategoryCommand command = new DeleteCategoryCommand("aaa");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
