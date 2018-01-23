package fr.knowledge.domain.scores.commands;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.valueobjects.Mark;

public class AddScoreCommand {
  private final String giver;
  private final ContentType contentType;
  private final String contentId;
  private final Mark mark;

  public AddScoreCommand(String giver, ContentType contentType, String contentId, Mark mark) {
    this.giver = giver;
    this.contentType = contentType;
    this.contentId = contentId;
    this.mark = mark;
  }

  public String getGiver() {
    return giver;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public String getContentId() {
    return contentId;
  }

  public Mark getMark() {
    return mark;
  }
}
