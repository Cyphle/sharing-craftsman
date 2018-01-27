package fr.knowledge.domain.comments.commands;

public class DeleteCommentCommand {
  private final String id;

  public DeleteCommentCommand(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
