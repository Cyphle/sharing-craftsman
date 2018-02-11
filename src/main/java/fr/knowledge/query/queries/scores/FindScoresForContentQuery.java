package fr.knowledge.query.queries.scores;

import fr.knowledge.query.queries.Query;

public class FindScoresForContentQuery implements Query {
  private String id;

  public FindScoresForContentQuery(String id) {
    this.id = id;
  }

  public String getContentId() {
    return id;
  }
}
