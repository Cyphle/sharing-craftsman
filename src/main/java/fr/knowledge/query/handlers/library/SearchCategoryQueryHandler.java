package fr.knowledge.query.handlers.library;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.library.SearchCategoryQuery;
import fr.knowledge.query.services.CategoryQueryService;

import java.util.List;

public class SearchCategoryQueryHandler implements QueryHandler<CategoryElastic> {
  private CategoryQueryService categoryQueryService;

  public SearchCategoryQueryHandler(CategoryQueryService categoryQueryService) {
    this.categoryQueryService = categoryQueryService;
  }

  @Override
  public List<CategoryElastic> handle(Query query) {
    return categoryQueryService.search(((SearchCategoryQuery) query).getCriteria());
  }
}
