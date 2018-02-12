package fr.knowledge.query.queries;

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class SearchCriteria {
  private Map<SearchKey, String> criteria;

  public SearchCriteria() {
    criteria = new HashMap<>();
  }

  public void with(SearchKey searchKey, String searchValue) {
    criteria.put(searchKey, searchValue);
  }

  public Map<SearchKey, String> getCriteria() {
    return criteria;
  }
}
