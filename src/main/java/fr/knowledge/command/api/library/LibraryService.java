package fr.knowledge.command.api.library;

import fr.knowledge.remote.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
  private UserService userService;

  @Autowired
  public LibraryService(UserService userService) {
    this.userService = userService;
  }

  public ResponseEntity createCategory(CategoryDTO categoryDTO) {
    return null;
  }
}
