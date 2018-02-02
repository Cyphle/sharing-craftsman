package fr.knowledge.command.api.scores;

public class ScoreDTO {
  private String id;
  private String giver;
  private String contentType;
  private String contentId;
  private int mark;

  public ScoreDTO() {
  }

  public ScoreDTO(String giver, String contentType, String contentId, int mark) {
    this.giver = giver;
    this.contentType = contentType;
    this.contentId = contentId;
    this.mark = mark;
  }

  public ScoreDTO(String id, String giver, int mark) {
    this.id = id;
    this.giver = giver;
    this.mark = mark;
  }

  public String getGiver() {
    return giver;
  }

  public String getContentType() {
    return contentType;
  }

  public String getContentId() {
    return contentId;
  }

  public int getMark() {
    return mark;
  }

  public static ScoreDTO from(String giver, String contentType, String contentId, int mark) {
    return new ScoreDTO(giver, contentType, contentId, mark);
  }

  public static ScoreDTO from(String id, String giver, int mark) {
    return new ScoreDTO(id, giver, mark);
  }
}
