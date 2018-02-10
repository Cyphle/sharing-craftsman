package fr.knowledge.query.bus;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SimpleQueryBus implements QueryBus {
  private Map<Class<? extends DomainCommand>, CommandHandler> handlers;

  public SimpleQueryBus() {
    this.handlers = new HashMap<>();
  }

  @Override
  public Map<Class<? extends DomainCommand>, CommandHandler> getHandlers() {
    return handlers;
  }

  @Override
  public void subscribe(Class<? extends DomainCommand> commandClass, CommandHandler commandHandler) {
    handlers.put(commandClass, commandHandler);
  }

  @Override
  public void send(DomainCommand command) throws Exception {
    handlers.get(command.getClass()).handle(command);
  }
}
