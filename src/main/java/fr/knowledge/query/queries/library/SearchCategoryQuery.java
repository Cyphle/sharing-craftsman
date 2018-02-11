package fr.knowledge.query.queries.library;

import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.SearchCriteria;

public class SearchCategoryQuery implements Query {
  private SearchCriteria searchCriteria;

  public SearchCategoryQuery(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public SearchCriteria getCriteria() {
    return searchCriteria;
  }
}
