package fr.knowledge.query.queries.scores;

import fr.knowledge.query.queries.Query;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class FindScoresForContentQuery implements Query {
  private final String id;

  public FindScoresForContentQuery(String id) {
    this.id = id;
  }

  public String getContentId() {
    return id;
  }
}
