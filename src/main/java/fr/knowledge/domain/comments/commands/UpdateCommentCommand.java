package fr.knowledge.domain.comments.commands;

import fr.knowledge.domain.common.DomainCommand;

import java.util.Objects;

public class UpdateCommentCommand implements DomainCommand {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateCommentCommand that = (UpdateCommentCommand) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(commenter, that.commenter) &&
            Objects.equals(newContent, that.newContent);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, commenter, newContent);
  }

  @Override
  public String toString() {
    return "UpdateCommentCommand{" +
            "id='" + id + '\'' +
            ", commenter='" + commenter + '\'' +
            ", newContent='" + newContent + '\'' +
            '}';
  }
}
