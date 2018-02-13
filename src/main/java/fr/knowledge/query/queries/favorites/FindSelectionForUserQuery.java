package fr.knowledge.query.queries.favorites;

import fr.knowledge.query.queries.Query;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class FindSelectionForUserQuery implements Query {
  private final String username;

  public FindSelectionForUserQuery(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
