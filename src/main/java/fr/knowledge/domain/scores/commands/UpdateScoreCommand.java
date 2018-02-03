package fr.knowledge.domain.scores.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.scores.valueobjects.Mark;

import java.util.Objects;

public class UpdateScoreCommand implements DomainCommand {
  private final String id;
  private final String giver;
  private final Mark newMark;

  public UpdateScoreCommand(String id, String giver, Mark newMark) {
    this.id = id;
    this.giver = giver;
    this.newMark = newMark;
  }

  public String getId() {
    return id;
  }

  public String getGiver() {
    return giver;
  }

  public Mark getMark() {
    return newMark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateScoreCommand that = (UpdateScoreCommand) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(giver, that.giver) &&
            newMark == that.newMark;
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, giver, newMark);
  }
}
