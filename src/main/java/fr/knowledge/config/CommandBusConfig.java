package fr.knowledge.config;

import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandBusConfig {
  private final Map<Class<? extends DomainCommand>, CommandHandler> handlers;

  public CommandBusConfig() {
    this.handlers = new HashMap<>();
  }

  public Map<Class<? extends DomainCommand>, CommandHandler> getHandlers() {
    return handlers;
  }
}
