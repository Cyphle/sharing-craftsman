package fr.knowledge.command.api.common;

import fr.knowledge.remote.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
  private final UserService userService;

  @Autowired
  public AuthorizationService(UserService userService) {
    this.userService = userService;
  }

  public boolean isUserAuthorized(AuthorizationInfoDTO authorizationInfoDTO) {
    if (!userService.verifyToken(authorizationInfoDTO.getClient(), authorizationInfoDTO.getClientSecret(), authorizationInfoDTO.getUsername(), authorizationInfoDTO.getAccessToken()).getStatusCode().equals(HttpStatus.OK)) {
      return false;
    }

    ResponseEntity responseEntity = userService.getAuthorizationsOfUser(authorizationInfoDTO.getClient(), authorizationInfoDTO.getClientSecret(), authorizationInfoDTO.getUsername(), authorizationInfoDTO.getAccessToken());
    AuthorizationsDTO authorizationsDTO = (AuthorizationsDTO) responseEntity.getBody();
    return authorizationsDTO == null || authorizationsDTO.hasUserRole();
  }


  public boolean areUsernameEquals(String first, String second) {
    return first.equals(second);
  }
}
