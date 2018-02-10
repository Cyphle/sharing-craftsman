package fr.knowledge.query.handlers;

import fr.knowledge.query.queries.Query;

import java.util.List;

public interface QueryHandler<T> {
  List<T> handle(Query query);
}
