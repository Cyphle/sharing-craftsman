package fr.knowledge.command.bus;

import fr.knowledge.config.CommandBusConfig;
import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SimpleCommandBus implements CommandBus {
  private Map<Class<? extends DomainCommand>, CommandHandler> handlers;

  public SimpleCommandBus() {
    handlers = new HashMap<>();
  }

  @Override
  public void initialize(CommandBusConfig commandBusConfig) {
    handlers = commandBusConfig.getHandlers();
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

  @Override
  public void emptyHandlers() {
    handlers = new HashMap<>();
  }
}
