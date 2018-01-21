package fr.knowledge.domain.library.ports;

import fr.knowledge.domain.library.aggregates.Category;

import java.util.List;

public interface CategoryRepository {
  List<Category> getAll();

  void save(Category category);
}
