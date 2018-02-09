package fr.knowledge.domain.scores.handlers;

import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.domain.scores.aggregates.Score;
import fr.knowledge.domain.scores.commands.AddScoreCommand;
import fr.knowledge.domain.scores.events.ScoreCreatedEvent;
import fr.knowledge.domain.scores.exceptions.AlreadyGivenScoreException;
import fr.knowledge.domain.scores.ports.ScoreRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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
  public void setUp() {
    given(idGenerator.generate()).willReturn("aaa");
    given(scoreRepository.getAll()).willReturn(Lists.emptyList());
    addScoreCommandHandler = new AddScoreCommandHandler(idGenerator, scoreRepository);
  }

  @Test
  public void should_add_score_to_category() throws Exception {
    AddScoreCommand command = new AddScoreCommand("john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE);

    addScoreCommandHandler.handle(command);

    Score score = Score.newScore("aaa", "john@doe.fr", ContentType.CATEGORY, "aaa", Mark.FIVE);
    verify(scoreRepository).save(score);
  }

  @Test(expected = AlreadyGivenScoreException.class)
  public void should_throw_exception_if_giver_already_gave_score_to_content() throws Exception {
    given(scoreRepository.getAll()).willReturn(Collections.singletonList(Score.of("aaa", "john@doe.fr", ContentType.CATEGORY, "caa", Mark.FOUR)));

    AddScoreCommand command = new AddScoreCommand("john@doe.fr", ContentType.CATEGORY, "caa", Mark.FIVE);

    addScoreCommandHandler.handle(command);
  }
}
