package fr.knowledge.domain.favorites.handlers;

import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.commands.AddToMySelectionCommand;
import fr.knowledge.domain.favorites.exceptions.AlreadyExistingSelectionException;
import fr.knowledge.domain.favorites.ports.SelectionRepository;

import java.util.Optional;

public class AddToMySelectionCommandHandler {
  private SelectionRepository selectionRepository;
  private IdGenerator idGenerator;

  public AddToMySelectionCommandHandler(IdGenerator idGenerator, SelectionRepository selectionRepository) {
    this.idGenerator = idGenerator;
    this.selectionRepository = selectionRepository;
  }

  public void handle(AddToMySelectionCommand command) throws AlreadyExistingSelectionException {
    Optional<Selection> selection = selectionRepository.get(Username.from(command.getUsername()), command.getSelectionType(), Id.of(command.getContentId()));

    if (selection.isPresent())
      throw new AlreadyExistingSelectionException();

    Selection newSelection = Selection.newSelection(idGenerator.generate(), command.getUsername(), command.getSelectionType(), command.getContentId());
    selectionRepository.save(newSelection);
  }
}
