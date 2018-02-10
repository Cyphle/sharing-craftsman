package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.exceptions.ScoreException;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.DeleteScoreCommand;
import fr.knowledge.domain.scores.ports.ScoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteScoreCommandHandlerTest {
  @Mock
  private ScoreRepository scoreRepository;
  private DeleteScoreCommandHandler deleteScoreCommandHandler;

  @Before
  public void setUp() {
    given(scoreRepository.get(Id.of("aaa"))).willReturn(Optional.of(Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE)));
    deleteScoreCommandHandler = new DeleteScoreCommandHandler(scoreRepository);
  }

  @Test
  public void should_delete_score() throws Exception {
    DeleteScoreCommand command = new DeleteScoreCommand("aaa", "john@doe.fr");

    deleteScoreCommandHandler.handle(command);

    Score score = Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE);
    score.delete(Username.from("john@doe.fr"));
    verify(scoreRepository).save(score);
  }

  @Test
  public void should_not_delete_score_if_giver_is_not_right() throws Exception {
    DeleteScoreCommand command = new DeleteScoreCommand("aaa", "foo@bar.fr");

    try {
      deleteScoreCommandHandler.handle(command);
      fail("Should have throw score exception");
    } catch (ScoreException e) {
      assertThat(e.getMessage()).isEqualTo("Wrong user.");
    }
  }
}
