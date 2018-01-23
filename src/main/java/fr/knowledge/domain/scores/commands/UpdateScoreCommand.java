package fr.knowledge.domain.scores.commands;

import fr.knowledge.domain.scores.valueobjects.Mark;

public class UpdateScoreCommand {
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
}
