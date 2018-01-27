package fr.knowledge.domain.favorites.handlers;

import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.commands.RemoveFromMySelectionCommand;
import fr.knowledge.domain.favorites.exceptions.SelectionNotFoundException;
import fr.knowledge.domain.favorites.ports.SelectionRepository;

class RemoveFromMySelectionCommandHandler {
  private final SelectionRepository selectionRepository;

  public RemoveFromMySelectionCommandHandler(SelectionRepository selectionRepository) {
    this.selectionRepository = selectionRepository;
  }

  public void handle(RemoveFromMySelectionCommand command) throws SelectionNotFoundException {
    Selection selection = selectionRepository.get(command.getId(), command.getUsername())
            .orElseThrow(SelectionNotFoundException::new);
    selection.delete();
    selectionRepository.save(selection);
  }
}
