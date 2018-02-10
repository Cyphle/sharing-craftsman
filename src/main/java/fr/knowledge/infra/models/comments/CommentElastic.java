package fr.knowledge.infra.models.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CommentElastic {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "commenter")
  private String commenter;
  @JsonProperty(value = "contentType")
  private String contentType;
  @JsonProperty(value = "contentId")
  private String contentId;
  @JsonProperty(value = "content")
  private String content;

  public CommentElastic() {
  }

  private CommentElastic(String id, String commenter, String contentType, String contentId, String content) {
    this.id = id;
    this.commenter = commenter;
    this.contentType = contentType;
    this.contentId = contentId;
    this.content = content;
  }

  public static CommentElastic of(String id, String commenter, String contentType, String contentId, String content) {
    return new CommentElastic(id, commenter, contentType, contentId, content);
  }
}
