package fr.knowledge.domain.common.valueobjects;

public class Id {
  private String id;

  public Id(String id) {
    this.id = id;
  }

  public static Id of(String id) {
    return new Id(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Id id1 = (Id) o;

    return id != null ? id.equals(id1.id) : id1.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Id{" +
            "id='" + id + '\'' +
            '}';
  }
}
