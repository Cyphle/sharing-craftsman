package fr.knowledge.query.queries;

public enum SearchKey {
  CategoryName("name"), KnowledgeTitle("knowledges.title"), KnowledgeContent("knowledges.content");

  public final String searchKey;

  SearchKey(String searchKey) {
    this.searchKey = searchKey;
  }
}
