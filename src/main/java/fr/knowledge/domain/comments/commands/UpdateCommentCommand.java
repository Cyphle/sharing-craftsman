package fr.knowledge.domain.comments.commands;

public class UpdateCommentCommand {
  private final String id;
  private final String newContent;

  public UpdateCommentCommand(String id, String newContent) {
    this.id = id;
    this.newContent = newContent;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return newContent;
  }
}
