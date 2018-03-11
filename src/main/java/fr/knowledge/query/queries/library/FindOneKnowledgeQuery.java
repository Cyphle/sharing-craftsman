package fr.knowledge.query.queries.library;

import fr.knowledge.query.queries.Query;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class FindOneKnowledgeQuery implements Query {
  private String knowledgeId;

  public FindOneKnowledgeQuery(String knowledgeId) {
    this.knowledgeId = knowledgeId;
  }

  public String getId() {
    return knowledgeId;
  }
}
