package fr.knowledge.query.api.library;

import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode
public class SearchCriteriaDTO {
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
}
