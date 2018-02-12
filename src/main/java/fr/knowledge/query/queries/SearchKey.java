package fr.knowledge.query.queries;

import java.util.Arrays;

public enum SearchKey {
  None(""), CategoryName("name"), KnowledgeTitle("knowledges.title"), KnowledgeContent("knowledges.content");

  public final String searchKey;

  SearchKey(String searchKey) {
    this.searchKey = searchKey;
  }

  public static SearchKey findFromName(String name) {
    return Arrays.stream(values())
            .filter(key -> key.name().toUpperCase().equals(name.toUpperCase()))
            .findAny()
            .orElse(SearchKey.None);
  }
}
