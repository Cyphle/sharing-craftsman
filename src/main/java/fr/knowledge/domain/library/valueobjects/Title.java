package fr.knowledge.domain.library.valueobjects;

public class Title {
  private final String title;

  private Title(String title) {
    this.title = title;
  }

  public boolean isEmpty() {
    return title.isEmpty();
  }

  public static Title of(String title) {
    return new Title(title);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Title title1 = (Title) o;

    return title != null ? title.equals(title1.title) : title1.title == null;
  }

  @Override
  public int hashCode() {
    return title != null ? title.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Title{" +
            "title='" + title + '\'' +
            '}';
  }
}
