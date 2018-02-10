package fr.knowledge.infra.repositories;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MockCategory {
  private final String id;
  private final String name;

  public MockCategory(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
