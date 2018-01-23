package fr.knowledge.domain.favorites.ports;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import fr.knowledge.domain.favorites.SelectionType;
import fr.knowledge.domain.favorites.aggregates.Selection;

import java.util.Optional;

public interface SelectionRepository {
  Optional<Selection> get(Id id, Username username);

  Optional<Selection> get(Username username, SelectionType selectionType, Id id);

  void save(Selection selection);
}
