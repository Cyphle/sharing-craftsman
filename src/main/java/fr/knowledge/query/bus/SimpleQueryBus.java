package fr.knowledge.query.bus;

import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimpleQueryBus implements QueryBus {
  private final Map<Class<? extends Query>, QueryHandler> handlers;

  public SimpleQueryBus() {
    this.handlers = new HashMap<>();
  }

  @Override
  public Map<Class<? extends Query>, QueryHandler> getHandlers() {
    return handlers;
  }

  @Override
  public void subscribe(Class<? extends Query> queryClass, QueryHandler queryHandler) {
    handlers.put(queryClass, queryHandler);
  }

  @Override
  public List send(Query query) {
    return handlers.get(query.getClass()).handle(query);
  }
}
