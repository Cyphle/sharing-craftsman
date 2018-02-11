package fr.knowledge.query.queries.comments;

import fr.knowledge.query.queries.Query;

public class FindCommentsForContentQuery implements Query {
  private String id;

  public FindCommentsForContentQuery(String id) {
    this.id = id;
  }

  public String getContentId() {
    return id;
  }
}
