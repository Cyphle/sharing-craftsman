package fr.knowledge.query.queries;

public class SearchCategoryQuery implements Query {
  private SearchCriteria searchCriteria;

  public SearchCategoryQuery(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public SearchCriteria getCriteria() {
    return searchCriteria;
  }
}
