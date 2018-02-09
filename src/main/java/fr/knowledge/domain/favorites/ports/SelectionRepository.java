package fr.knowledge.domain.favorites.ports;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.favorites.aggregates.Selection;

import java.util.List;
import java.util.Optional;

public interface SelectionRepository {
  Optional<Selection> get(Id aggregateId);

  List<Selection> getAll();

  void save(Selection selection);
}
