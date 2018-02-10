package fr.knowledge.infra.models.scores;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ScoreElastic {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "giver")
  private String giver;
  @JsonProperty(value = "contentType")
  private String contentType;
  @JsonProperty(value = "contentId")
  private String contentId;
  @JsonProperty(value = "mark")
  private int mark;

  public ScoreElastic() {
  }

  public ScoreElastic(String id, String giver, String contentType, String contentId, int mark) {
    this.id = id;
    this.giver = giver;
    this.contentType = contentType;
    this.contentId = contentId;
    this.mark = mark;
  }

  public static ScoreElastic of(String id, String giver, String contentType, String contentId, int mark) {
    return new ScoreElastic(id, giver, contentType, contentId, mark);
  }
}
