package fr.knowledge.domain.favorites.handlers;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.commands.AddToMySelectionCommand;
import fr.knowledge.domain.favorites.exceptions.AlreadyExistingSelectionException;
import fr.knowledge.domain.favorites.ports.SelectionRepository;

import java.util.List;
import java.util.Optional;

public class AddToMySelectionCommandHandler implements CommandHandler {
  private final SelectionRepository selectionRepository;
  private final IdGenerator idGenerator;

  public AddToMySelectionCommandHandler(IdGenerator idGenerator, SelectionRepository selectionRepository) {
    this.idGenerator = idGenerator;
    this.selectionRepository = selectionRepository;
  }

  @Override
  public void handle(DomainCommand command) throws AlreadyExistingSelectionException {
    List<Selection> selections = selectionRepository.getAll();

    boolean doesExists = selections.stream()
            .anyMatch(((AddToMySelectionCommand) command)::hasSameProperties);
    if (doesExists)
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
