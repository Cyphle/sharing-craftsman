package fr.knowledge.command.bus;

import fr.knowledge.domain.common.DomainCommand;

public interface CommandBus {
  void send(DomainCommand command);
}
