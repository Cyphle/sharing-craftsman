package fr.knowledge.infra.models.library;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.library.valueobjects.Knowledge;
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

  private KnowledgeElastic(String id, String creator, String title, String content) {
    this.id = id;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public void update(KnowledgeElastic knowledge) {
    creator = knowledge.creator;
    title = knowledge.title;
    content = knowledge.content;
  }

  public static KnowledgeElastic of(String id, String creator, String title, String content) {
    return new KnowledgeElastic(id, creator, title, content);
  }

  public static KnowledgeElastic fromDomainToElastic(Knowledge knowledge) {
    return KnowledgeElastic.of(knowledge.getId().getId(), knowledge.getCreatorContent(), knowledge.getTitleContent(), knowledge.getContentContent());
  }
}
