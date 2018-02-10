package fr.knowledge.infra.models.library;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class CategoryElastic {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "name")
  private String name;
  @JsonProperty(value = "knowledges")
  private List<KnowledgeElastic> knowledges;

  public CategoryElastic() {
  }

  private CategoryElastic(String id, String name) {
    this.id = id;
    this.name = name;
    this.knowledges = new ArrayList<>();
  }

  private CategoryElastic(String id, String name, List<KnowledgeElastic> knowledges) {
    this.id = id;
    this.name = name;
    this.knowledges = knowledges;
  }

  public void addKnowledge(KnowledgeElastic knowledge) {
    knowledges.add(knowledge);
  }

  public static CategoryElastic of(String id, String name) {
    return new CategoryElastic(id, name);
  }

  public static CategoryElastic of(String id, String name, List<KnowledgeElastic> knowledges) {
    return new CategoryElastic(id, name, knowledges);
  }
}
