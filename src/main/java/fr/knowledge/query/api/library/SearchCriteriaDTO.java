package fr.knowledge.query.api.library;

import fr.knowledge.query.queries.SearchCriteria;
import fr.knowledge.query.queries.SearchKey;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode
class SearchCriteriaDTO {
  private Map<String, String> searchKeys;

  public SearchCriteriaDTO() {

  }

  public SearchCriteriaDTO(Map<String, String> searchKeys) {
    this.searchKeys = searchKeys;
  }

  public Map<String, String> getSearchKeys() {
    return searchKeys;
  }

  public void setSearchKeys(Map<String, String> searchKeys) {
    this.searchKeys = searchKeys;
  }

  public SearchCriteria fromApiToQuery() {
    SearchCriteria criteria = new SearchCriteria();
    searchKeys.forEach((key, value) -> {
      criteria.with(SearchKey.findFromName(key), value);
    });
    return criteria;
  }
}
