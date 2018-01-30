package fr.knowledge.command.api.library;

import fr.knowledge.remote.user.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceTest {
  @Mock
  private UserService userService;
  private LibraryService libraryService;

  @Before
  public void setUp() throws Exception {
    libraryService = new LibraryService(userService);
  }
}
