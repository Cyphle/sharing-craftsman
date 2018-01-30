package fr.knowledge.command.api.library;

import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.remote.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceTest {
  @Mock
  private UserService userService;
  @Mock
  private CommandBus commandBus;
  private LibraryService libraryService;

  @Before
  public void setUp() throws Exception {
    libraryService = new LibraryService(userService, commandBus);
  }

  @Test
  public void should_create_category() throws Exception {
    ResponseEntity response = libraryService.createCategory(CategoryDTO.from("Architecture"));

    CreateCategoryCommand command = new CreateCategoryCommand("Architecture");
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
