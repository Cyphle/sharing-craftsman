package fr.knowledge.domain.common;

public interface CommandHandler {
  void handle(DomainCommand command) throws Exception;
}
