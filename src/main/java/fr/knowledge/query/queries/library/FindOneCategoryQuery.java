package fr.knowledge.query.queries.library;

import fr.knowledge.query.queries.Query;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class FindOneCategoryQuery implements Query {
  private final String id;

  public FindOneCategoryQuery(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
