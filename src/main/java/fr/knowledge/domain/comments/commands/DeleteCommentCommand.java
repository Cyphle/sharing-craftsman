package fr.knowledge.domain.comments.commands;

import fr.knowledge.domain.common.DomainCommand;

import java.util.Objects;

public class DeleteCommentCommand implements DomainCommand {
  private final String id;
  private final String commenter;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeleteCommentCommand that = (DeleteCommentCommand) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(commenter, that.commenter);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, commenter);
  }

  @Override
  public String toString() {
    return "DeleteCommentCommand{" +
            "id='" + id + '\'' +
            ", commenter='" + commenter + '\'' +
            '}';
  }
}
