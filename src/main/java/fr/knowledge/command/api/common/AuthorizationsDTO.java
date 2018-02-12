package fr.knowledge.command.api.common;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class AuthorizationsDTO {
  private Set<GroupDTO> groups;

  private AuthorizationsDTO() {
    this.groups = new HashSet<>();
  }

  private AuthorizationsDTO(HashSet<GroupDTO> groups) {
    this.groups = groups;
  }

  public Set<GroupDTO> getGroups() {
    return groups;
  }

  public void setGroups(Set<GroupDTO> groups) {
    this.groups = groups;
  }

  public void addGroup(GroupDTO groupDTO) {
    groups.add(groupDTO);
  }

  public static AuthorizationsDTO from(HashSet<GroupDTO> groups) {
    return new AuthorizationsDTO(groups);
  }

  public boolean hasUserRole() {
    GroupDTO userGroup = groups.stream()
            .filter(group -> group.getName().equals("USERS"))
            .findAny()
            .orElse(GroupDTO.from(""));
    if (userGroup.getName().equals("USERS")) {
      RoleDTO roleUser = userGroup.getRoles()
              .stream()
              .filter(role -> role.getName().equals("ROLE_USER"))
              .findAny()
              .orElse(RoleDTO.from(""));

      return roleUser.getName().equals("ROLE_USER");
    }
    return false;
  }
}
