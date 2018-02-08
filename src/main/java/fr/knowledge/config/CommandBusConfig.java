package fr.knowledge.config;

import fr.knowledge.command.bus.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandBusConfig {
  @Autowired
  private CommandBus commandBus;

  public void configure() {
//    commandBus.subscribe();
    /*
    Subscribe all command handlers with their repositories (which need event bus maybe to have to be configured before)
     */
  }
}
