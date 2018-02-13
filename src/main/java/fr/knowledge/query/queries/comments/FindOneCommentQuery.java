package fr.knowledge.query.queries.comments;

import fr.knowledge.query.queries.Query;

public class FindOneCommentQuery implements Query {
  private final String id;

  public FindOneCommentQuery(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
