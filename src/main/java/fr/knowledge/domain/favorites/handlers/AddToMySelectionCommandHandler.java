package fr.knowledge.domain.favorites.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.utils.IdGenerator;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.commands.AddToMySelectionCommand;
import fr.knowledge.domain.favorites.exceptions.AlreadyExistingSelectionException;
import fr.knowledge.domain.favorites.ports.SelectionRepository;

import java.util.Optional;

class AddToMySelectionCommandHandler implements CommandHandler {
  private final SelectionRepository selectionRepository;
  private final IdGenerator idGenerator;

  public AddToMySelectionCommandHandler(IdGenerator idGenerator, SelectionRepository selectionRepository) {
    this.idGenerator = idGenerator;
    this.selectionRepository = selectionRepository;
  }

  @Override
  public void handle(DomainCommand command) throws AlreadyExistingSelectionException {
    Optional<Selection> selection = selectionRepository.get(
            Username.from(((AddToMySelectionCommand) command).getUsername()),
            ((AddToMySelectionCommand) command).getContentType(),
            Id.of(((AddToMySelectionCommand) command).getContentId())
    );

    if (selection.isPresent())
      throw new AlreadyExistingSelectionException();

    Selection newSelection = Selection.newSelection(
            idGenerator.generate(),
            ((AddToMySelectionCommand) command).getUsername(),
            ((AddToMySelectionCommand) command).getContentType(),
            ((AddToMySelectionCommand) command).getContentId()
    );
    selectionRepository.save(newSelection);
  }
}
