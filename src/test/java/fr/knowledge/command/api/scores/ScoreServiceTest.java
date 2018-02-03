package fr.knowledge.command.api.scores;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.commands.AddScoreCommand;
import fr.knowledge.domain.scores.valueobjects.Mark;
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
public class ScoreServiceTest {
  private ScoreService scoreService;
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
    scoreService = new ScoreService(authorizationService, commandBus);
  }

  @Test
  public void should_add_score() throws Exception {
    ResponseEntity response = scoreService.addScore(authorizationInfoDTO, ScoreDTO.from("john@doe.fr", ContentType.CATEGORY.name(), "aaa", Mark.FIVE.value), "john@doe.fr");

    AddScoreCommand command = new AddScoreCommand("john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE);
    verify(commandBus).send(command);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
