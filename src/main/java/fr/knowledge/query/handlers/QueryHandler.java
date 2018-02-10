package fr.knowledge.query.handlers;

import fr.knowledge.query.queries.Query;

public interface QueryHandler {
  void handle(Query query);
}
