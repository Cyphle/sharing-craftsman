package fr.knowledge.query.handlers;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.query.queries.Query;
import fr.knowledge.query.services.CategoryQueryService;

import java.util.List;

public class FindAllCategoriesQueryHandler implements QueryHandler<CategoryElastic> {
  private CategoryQueryService categoryQueryService;

  public FindAllCategoriesQueryHandler(CategoryQueryService categoryQueryService) {
    this.categoryQueryService = categoryQueryService;
  }

  @Override
  public List<CategoryElastic> handle(Query query) {
    return categoryQueryService.findAllCategories();
  }
}
