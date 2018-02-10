package fr.knowledge.infra.models;

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

  public CategoryElastic(String id, String name) {
    this.id = id;
    this.name = name;
    this.knowledges = new ArrayList<>();
  }

  public CategoryElastic(String id, String name, List<KnowledgeElastic> knowledges) {
    this.id = id;
    this.name = name;
    this.knowledges = knowledges;
  }
}
