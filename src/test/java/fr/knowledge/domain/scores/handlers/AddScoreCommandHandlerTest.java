package fr.knowledge.domain.scores.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.Mark;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.AddScoreCommand;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.domain.scores.exceptions.AlreadyGivenScoreException;
import fr.knowledge.domain.scores.ports.ScoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddScoreCommandHandlerTest {
  @Mock
  private ScoreRepository scoreRepository;
  @Mock
  private IdGenerator idGenerator;
  private AddScoreCommandHandler addScoreCommandHandler;

  @Before
  public void setUp() throws Exception {
    given(idGenerator.generate()).willReturn("aaa");
    given(scoreRepository.get(Username.from("john@doe.fr"), Id.of("aaa"))).willReturn(Optional.empty());
    addScoreCommandHandler = new AddScoreCommandHandler(idGenerator, scoreRepository);
  }

  @Test
  public void should_add_score_to_category() throws Exception {
    AddScoreCommand command = new AddScoreCommand("john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE);

    addScoreCommandHandler.handle(command);

    Score score = Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE);
    score.saveChanges(new ScoreCreatedEvent(Id.of("aaa"), Username.from("john@doe.fr"), ContentType.CATEGORY, Id.of("aaa"), Mark.FIVE));
    verify(scoreRepository).save(score);
  }

  @Test
  public void should_throw_exception_if_giver_already_gave_score_to_content() throws Exception {
    given(scoreRepository.get(Username.from("john@doe.fr"), Id.of("aaa"))).willReturn(Optional.of(Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE)));
    AddScoreCommand command = new AddScoreCommand("john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE);

    try {
      addScoreCommandHandler.handle(command);
      fail("Should have thrown AlreadyGivenScoreException.");
    } catch (AlreadyGivenScoreException e) {
      assertThat(e).isNotNull();
    }
  }
}
