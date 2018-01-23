package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.Mark;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.UpdateScoreCommand;
import fr.knowledge.domain.scores.events.ScoreUpdatedEvent;
import fr.knowledge.domain.scores.exceptions.ScoreNotFoundException;
import fr.knowledge.domain.scores.ports.ScoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateScoreCommandHandlerTest {
  @Mock
  private ScoreRepository scoreRepository;
  private UpdateScoreCommandHandler updateScoreCommandHandler;

  @Before
  public void setUp() throws Exception {
    given(scoreRepository.get(Id.of("aaa"), Username.from("john@doe.fr"))).willReturn(Optional.of(Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", Mark.THREE)));
    updateScoreCommandHandler = new UpdateScoreCommandHandler(scoreRepository);
  }

  @Test
  public void should_update_score() throws Exception {
    UpdateScoreCommand command = new UpdateScoreCommand("aaa", "john@doe.fr", Mark.THREE);

    updateScoreCommandHandler.handle(command);

    Score updatedScore = Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", Mark.THREE);
    updatedScore.saveChanges(new ScoreUpdatedEvent(Id.of("aaa"), Mark.THREE));
    verify(scoreRepository).save(updatedScore);
  }

  @Test(expected = ScoreNotFoundException.class)
  public void should_throw_exception_if_score_does_not_exists() throws Exception {
    given(scoreRepository.get(Id.of("aaa"), Username.from("john@doe.fr"))).willReturn(Optional.empty());
    UpdateScoreCommand command = new UpdateScoreCommand("aaa", "john@doe.fr", Mark.THREE);

    updateScoreCommandHandler.handle(command);
  }
}
