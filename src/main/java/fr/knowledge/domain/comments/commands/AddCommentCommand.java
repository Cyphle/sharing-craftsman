package fr.knowledge.domain.comments.commands;

import fr.knowledge.domain.common.valueobjects.ContentType;

public class AddCommentCommand {
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
}
