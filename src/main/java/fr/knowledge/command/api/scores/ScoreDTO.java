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

  public ScoreDTO(String id, String giver) {
    this.id = id;
    this.giver = giver;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGiver() {
    return giver;
  }

  public void setGiver(String giver) {
    this.giver = giver;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public int getMark() {
    return mark;
  }

  public void setMark(int mark) {
    this.mark = mark;
  }

  public static ScoreDTO from(String giver, String contentType, String contentId, int mark) {
    return new ScoreDTO(giver, contentType, contentId, mark);
  }

  public static ScoreDTO from(String id, String giver, int mark) {
    return new ScoreDTO(id, giver, mark);
  }

  public static ScoreDTO from(String id, String giver) {
    return new ScoreDTO(id, giver);
  }
}
