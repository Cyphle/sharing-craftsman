package fr.knowledge.query.queries.library;

import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.SearchCriteria;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class SearchCategoryQuery implements Query {
  private final SearchCriteria searchCriteria;

  public SearchCategoryQuery(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public SearchCriteria getCriteria() {
    return searchCriteria;
  }
}
