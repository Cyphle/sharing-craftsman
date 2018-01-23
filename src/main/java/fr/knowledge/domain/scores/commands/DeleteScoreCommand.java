package fr.knowledge.domain.scores.commands;

public class DeleteScoreCommand {
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
}
