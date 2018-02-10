package fr.knowledge.domain.favorites.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.commands.RemoveFromMySelectionCommand;
import fr.knowledge.domain.favorites.exceptions.SelectionException;
import fr.knowledge.domain.favorites.exceptions.SelectionNotFoundException;
import fr.knowledge.domain.favorites.ports.SelectionRepository;

public class RemoveFromMySelectionCommandHandler implements CommandHandler {
  private final SelectionRepository selectionRepository;

  public RemoveFromMySelectionCommandHandler(SelectionRepository selectionRepository) {
    this.selectionRepository = selectionRepository;
  }

  public void handle(DomainCommand command) throws SelectionNotFoundException, SelectionException {
    Selection selection = selectionRepository.get(((RemoveFromMySelectionCommand) command).getId())
            .orElseThrow(SelectionNotFoundException::new);
    selection.delete(((RemoveFromMySelectionCommand) command).getUsername());
    selectionRepository.save(selection);
  }
}
