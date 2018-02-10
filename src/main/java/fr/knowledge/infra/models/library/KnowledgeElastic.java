package fr.knowledge.infra.models.library;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class KnowledgeElastic {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "creator")
  private String creator;
  @JsonProperty(value = "title")
  private String title;
  @JsonProperty(value = "content")
  private String content;

  public KnowledgeElastic() {
  }

  public KnowledgeElastic(String id, String creator, String title, String content) {
    this.id = id;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }
}
