package fr.knowledge.command.bus;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;

import java.util.Map;

public interface CommandBus {
  Map<Class<? extends DomainCommand>, CommandHandler> getHandlers();

  void subscribe(Class<? extends DomainCommand> commandClass, CommandHandler commandHandler);

  void send(DomainCommand command) throws Exception;

  void emptyHandlers();
}
