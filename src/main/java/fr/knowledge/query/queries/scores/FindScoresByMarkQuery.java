package fr.knowledge.query.queries.scores;

import fr.knowledge.query.queries.Query;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class FindScoresByMarkQuery implements Query {
  private int mark;

  public FindScoresByMarkQuery(int mark) {
    this.mark = mark;
  }

  public int getMark() {
    return mark;
  }
}
