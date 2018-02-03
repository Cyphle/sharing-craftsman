package fr.knowledge.command.common;

import com.google.common.collect.Sets;
import fr.knowledge.command.api.common.*;
import fr.knowledge.remote.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationServiceTest {
  private AuthorizationInfoDTO authorizationInfoDTO;
  @Mock
  private UserService userService;
  private AuthorizationService authorizationService;

  @Before
  public void setUp() throws Exception {
    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );

    authorizationService = new AuthorizationService(userService);
  }

  @Test
  public void should_return_ok_if_user_is_authorized_with_user_role() throws Exception {
    given(userService.verifyToken("client", "secret", "john@doe.fr", "token")).willReturn(ResponseEntity.ok().build());
    AuthorizationsDTO authorizationsDTO = AuthorizationsDTO.from(
            Sets.newHashSet(GroupDTO.from("USERS", Sets.newHashSet(RoleDTO.from("ROLE_USER"))))
    );
    given(userService.getAuthorizationsOfUser("client", "secret", "john@doe.fr", "token")).willReturn(ResponseEntity.ok(authorizationsDTO));

    assertThat(authorizationService.isUserAuthorized(authorizationInfoDTO)).isTrue();
  }

  @Test
  public void should_return_unauthorized_if_user_is_not_authorized() throws Exception {
    given(userService.verifyToken("client", "secret", "john@doe.fr", "token")).willReturn(new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED));

    assertThat(authorizationService.isUserAuthorized(authorizationInfoDTO)).isFalse();
  }

  @Test
  public void should_return_unauthorized_if_user_has_not_role_user() throws Exception {
    given(userService.verifyToken("client", "secret", "john@doe.fr", "token")).willReturn(ResponseEntity.ok().build());
    AuthorizationsDTO authorizationsDTO = AuthorizationsDTO.from(
            Sets.newHashSet(GroupDTO.from("", Sets.newHashSet()))
    );
    given(userService.getAuthorizationsOfUser("client", "secret", "john@doe.fr", "token")).willReturn(ResponseEntity.ok(authorizationsDTO));


    assertThat(authorizationService.isUserAuthorized(authorizationInfoDTO)).isFalse();
  }

  @Test
  public void should_return_true_if_both_username_are_the_same() throws Exception {
    assertThat(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).isTrue();
  }
}
