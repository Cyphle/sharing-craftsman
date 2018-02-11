package fr.knowledge.query.queries.favorites;

import fr.knowledge.query.queries.Query;

public class FindSelectionForUserQuery implements Query {
  private String username;

  public FindSelectionForUserQuery(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
