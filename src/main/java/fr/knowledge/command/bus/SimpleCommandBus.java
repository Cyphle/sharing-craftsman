package fr.knowledge.command.bus;

import fr.knowledge.domain.common.DomainCommand;
import org.springframework.stereotype.Service;

@Service
public class SimpleCommandBus implements CommandBus {
  @Override
  public void send(DomainCommand command) {

  }
}
