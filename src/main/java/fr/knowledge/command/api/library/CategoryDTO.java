package fr.knowledge.command.api.library;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CategoryDTO {
  private String id;
  private String name;

  public CategoryDTO() {
  }

  public CategoryDTO(String id, String name) {
    this.id = id;
    this.name = name;
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

  public static CategoryDTO from(String id, String name) {
    return new CategoryDTO(id, name);
  }
}
