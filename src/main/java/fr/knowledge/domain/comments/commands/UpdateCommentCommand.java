package fr.knowledge.domain.comments.commands;

public class UpdateCommentCommand {
  private final String id;
  private String commenter;
  private final String newContent;

  public UpdateCommentCommand(String id, String commenter, String newContent) {
    this.id = id;
    this.commenter = commenter;
    this.newContent = newContent;
  }

  public String getId() {
    return id;
  }

  public String getCommenter() {
    return commenter;
  }

  public String getContent() {
    return newContent;
  }
}
