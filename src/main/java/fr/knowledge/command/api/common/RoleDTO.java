package fr.knowledge.command.api.common;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class RoleDTO {
  private String name;

  private RoleDTO() {
  }

  private RoleDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static RoleDTO from(String name) {
    return new RoleDTO(name);
  }
}
