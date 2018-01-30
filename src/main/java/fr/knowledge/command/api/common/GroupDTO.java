package fr.knowledge.command.api.common;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class GroupDTO {
  private Set<RoleDTO> roles;
  private String name;

  private GroupDTO() {
    this.roles = new HashSet<>();
  }

  private GroupDTO(String name) {
    this.name = name;
    this.roles = new HashSet<>();
  }

  private GroupDTO(String name, Set<RoleDTO> roles) {
    this.name = name;
    this.roles = roles;
  }

  public String getName() {
    return name;
  }

  public Set<RoleDTO> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleDTO> roles) {
    this.roles = roles;
  }

  public void addRoles(List<RoleDTO> roles) {
    this.roles.addAll(roles);
  }

  public void addRole(RoleDTO role) {
    roles.add(role);
  }

  public static GroupDTO from(String name) {
    return new GroupDTO(name);
  }

  public static GroupDTO from(String name, Set<RoleDTO> roles) {
    return new GroupDTO(name, roles);
  }
}
