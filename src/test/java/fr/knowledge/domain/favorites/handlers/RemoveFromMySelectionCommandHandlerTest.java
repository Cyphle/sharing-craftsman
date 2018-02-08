package fr.knowledge.domain.favorites.handlers;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.commands.RemoveFromMySelectionCommand;
import fr.knowledge.domain.favorites.exceptions.SelectionException;
import fr.knowledge.domain.favorites.ports.SelectionRepository;
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
public class RemoveFromMySelectionCommandHandlerTest {
  @Mock
  private SelectionRepository selectionRepository;
  private RemoveFromMySelectionCommandHandler removeFromMySelectionCommandHandler;

  @Before
  public void setUp() {
    given(selectionRepository.get(Id.of("aaa"))).willReturn(Optional.of(Selection.of("aaa", "john@doe.fr", ContentType.KNOWLEDGE, "aaa")));
    removeFromMySelectionCommandHandler = new RemoveFromMySelectionCommandHandler(selectionRepository);
  }

  @Test
  public void should_remove_selection_from_my_selection() throws Exception {
    RemoveFromMySelectionCommand command = new RemoveFromMySelectionCommand("aaa", "john@doe.fr");

    removeFromMySelectionCommandHandler.handle(command);

    Selection deletedSelection = Selection.of("aaa", "john@doe.fr", ContentType.KNOWLEDGE, "aaa");
    deletedSelection.delete(command.getUsername());
    verify(selectionRepository).save(deletedSelection);
  }

  @Test
  public void should_not_remove_selection_if_user_is_not_right() throws Exception {
    RemoveFromMySelectionCommand command = new RemoveFromMySelectionCommand("aaa", "foo@bar.fr");

    try {
      removeFromMySelectionCommandHandler.handle(command);
      fail("Should have throw selection exception");
    } catch (SelectionException e) {
      assertThat(e.getMessage()).isEqualTo("Wrong user.");
    }
  }
}
