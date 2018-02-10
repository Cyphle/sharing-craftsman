package fr.knowledge.query.queries;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class FindOneCategoryQuery implements Query {
  private String id;

  public FindOneCategoryQuery(String id) {
    this.id = id;
  }
}