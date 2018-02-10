package fr.knowledge.query.bus;

import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;

import java.util.Map;

public interface QueryBus {
  Map<Class<? extends Query>, QueryHandler> getHandlers();

  void subscribe(Class<? extends Query> queryClass, QueryHandler queryHandler);

  void send(Query query);
}
