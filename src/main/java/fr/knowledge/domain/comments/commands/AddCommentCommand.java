package fr.knowledge.domain.comments.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.ContentType;

import java.util.Objects;

public class AddCommentCommand implements DomainCommand {
  private final String commenter;
  private final ContentType contentType;
  private final String contentId;
  private final String content;

  public AddCommentCommand(String commenter, ContentType contentType, String contentId, String content) {
    this.commenter = commenter;
    this.contentType = contentType;
    this.contentId = contentId;
    this.content = content;
  }

  public String getCommenter() {
    return commenter;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public String getContentId() {
    return contentId;
  }

  public String getContent() {
    return content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddCommentCommand that = (AddCommentCommand) o;
    return Objects.equals(commenter, that.commenter) &&
            contentType == that.contentType &&
            Objects.equals(contentId, that.contentId) &&
            Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commenter, contentType, contentId, content);
  }

  @Override
  public String toString() {
    return "AddCommentCommand{" +
            "commenter='" + commenter + '\'' +
            ", contentType=" + contentType +
            ", contentId='" + contentId + '\'' +
            ", content='" + content + '\'' +
            '}';
  }
}
