package fr.knowledge.domain.comments.commands;

public class DeleteCommentCommand {
  private final String id;
  private String commenter;

  public DeleteCommentCommand(String id, String commenter) {
    this.id = id;
    this.commenter = commenter;
  }

  public String getId() {
    return id;
  }

  public String getCommenter() {
    return commenter;
  }
}
