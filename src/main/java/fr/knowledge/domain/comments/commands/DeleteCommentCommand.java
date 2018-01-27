package fr.knowledge.domain.comments.commands;

public class DeleteCommentCommand {
  private String id;

  public DeleteCommentCommand(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
