package fr.knowledge.domain.scores.commands;

import fr.knowledge.domain.common.DomainCommand;

import java.util.Objects;

public class DeleteScoreCommand implements DomainCommand {
  private final String id;
  private final String giver;

  public DeleteScoreCommand(String id, String giver) {
    this.id = id;
    this.giver = giver;
  }

  public String getId() {
    return id;
  }

  public String getGiver() {
    return giver;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeleteScoreCommand that = (DeleteScoreCommand) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(giver, that.giver);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, giver);
  }
}
