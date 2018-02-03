package fr.knowledge.domain.scores.commands;

import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.valueobjects.Mark;

import java.util.Objects;

public class AddScoreCommand implements DomainCommand {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddScoreCommand that = (AddScoreCommand) o;
    return Objects.equals(giver, that.giver) &&
            contentType == that.contentType &&
            Objects.equals(contentId, that.contentId) &&
            mark == that.mark;
  }

  @Override
  public int hashCode() {

    return Objects.hash(giver, contentType, contentId, mark);
  }
}
