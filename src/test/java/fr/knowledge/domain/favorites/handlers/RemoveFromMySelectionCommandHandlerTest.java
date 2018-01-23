package fr.knowledge.domain.favorites.handlers;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.SelectionType;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.commands.RemoveFromMySelectionCommand;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;
import fr.knowledge.domain.favorites.ports.SelectionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RemoveFromMySelectionCommandHandlerTest {
  @Mock
  private SelectionRepository selectionRepository;
  private RemoveFromMySelectionCommandHandler removeFromMySelectionCommandHandler;

  @Before
  public void setUp() throws Exception {
    given(selectionRepository.get(Id.of("aaa"), Username.from("john@doe.fr"))).willReturn(Optional.of(Selection.of("aaa", "john@doe.fr", SelectionType.KNOWLEDGE, "aaa")));
    removeFromMySelectionCommandHandler = new RemoveFromMySelectionCommandHandler(selectionRepository);
  }

  @Test
  public void should_remove_selection_from_my_selection() throws Exception {
    RemoveFromMySelectionCommand command = new RemoveFromMySelectionCommand("aaa", "john@doe.fr");

    removeFromMySelectionCommandHandler.handle(command);

    Selection deletedSelection = Selection.of("aaa", "john@doe.fr", SelectionType.KNOWLEDGE, "aaa");
    deletedSelection.saveChanges(new SelectionRemovedEvent(Id.of("aaa")));
    verify(selectionRepository).save(deletedSelection);
  }
}
