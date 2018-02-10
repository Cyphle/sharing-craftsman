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

  private CategoryDTO(String id, String name) {
    this.id = id;
    this.name = name;
  }

  private CategoryDTO(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
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

  public static CategoryDTO fromId(String id) {
    return new CategoryDTO(id, "");
  }
}
