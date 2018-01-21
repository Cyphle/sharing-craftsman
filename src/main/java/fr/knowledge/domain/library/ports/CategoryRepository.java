package fr.knowledge.domain.library.ports;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.aggregates.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
  List<Category> getAll();

  void save(Category category);

  Optional<Category> get(Id aggregateId);
}
