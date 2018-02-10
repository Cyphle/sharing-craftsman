package fr.knowledge.infra.models.favorites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class SelectionElastic {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "username")
  private String username;
  @JsonProperty(value = "contentType")
  private String contentType;
  @JsonProperty(value = "contentId")
  private String contentId;

  public SelectionElastic() {
  }

  public SelectionElastic(String id, String username, String contentType, String contentId) {
    this.id = id;
    this.username = username;
    this.contentType = contentType;
    this.contentId = contentId;
  }

  public static SelectionElastic of(String id, String username, String contentType, String contentId) {
    return new SelectionElastic(id, username, contentType, contentId);
  }
}
