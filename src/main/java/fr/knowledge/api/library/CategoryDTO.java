package fr.knowledge.api.library;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CategoryDTO {
  private String name;

  public CategoryDTO() {
  }

  public CategoryDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static CategoryDTO from(String name) {
    return new CategoryDTO(name);
  }
}
