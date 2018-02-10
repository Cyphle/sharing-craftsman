package fr.knowledge.query.handlers;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.query.queries.Query;

import java.util.List;

public class SearchCategoryQueryHandler implements QueryHandler<CategoryElastic> {
  @Override
  public List<CategoryElastic> handle(Query query) {
    return null;
  }
}
