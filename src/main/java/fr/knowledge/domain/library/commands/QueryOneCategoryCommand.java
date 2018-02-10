package fr.knowledge.domain.library.commands;

import fr.knowledge.domain.common.DomainCommand;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class QueryOneCategoryCommand implements DomainCommand {
  private String id;

  public QueryOneCategoryCommand(String id) {
    this.id = id;
  }
}
