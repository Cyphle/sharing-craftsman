package fr.knowledge.infra.models.library;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  public void updateKnowledge(Knowledge knowledge) {
    knowledges.stream()
            .filter(knowledgeElastic -> knowledgeElastic.getId().equals(knowledge.getId().getId()))
            .findAny()
            .orElseThrow(RuntimeException::new)
            .update(KnowledgeElastic.fromDomainToElastic(knowledge));
  }

  public void deleteKnowledge(String knowledgeId) {
    knowledges = knowledges.stream()
            .filter(knowledgeElastic -> !knowledgeElastic.getId().equals(knowledgeId))
            .collect(Collectors.toList());
  }

  public static CategoryElastic of(String id, String name) {
    return new CategoryElastic(id, name);
  }

  public static CategoryElastic of(String id, String name, List<KnowledgeElastic> knowledges) {
    return new CategoryElastic(id, name, knowledges);
  }
}
