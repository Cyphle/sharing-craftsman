package fr.knowledge.domain.library.aggregates;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryCreatedEvent;
import fr.knowledge.domain.library.valueobjects.Name;

import java.util.ArrayList;
import java.util.List;

public class Category {
  private final Id id;
  private final Name name;
  private List<CategoryCreatedEvent> events;

  public Category(Id id, Name name) {
    this.id = id;
    this.name = name;
    events = new ArrayList<>();
  }

  public void addEvent(CategoryCreatedEvent categoryCreatedEvent) {
    events.add(categoryCreatedEvent);
  }

  public static Category of(String id, String name) {
    return new Category(Id.of(id), Name.of(name));
  }

  public static Category newCategory(String id, String name) {
    Category category = Category.of(id, name);
    category.addEvent(new CategoryCreatedEvent(Id.of(id), Name.of(name)));
    return category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Category category = (Category) o;

    if (id != null ? !id.equals(category.id) : category.id != null) return false;
    if (name != null ? !name.equals(category.name) : category.name != null) return false;
    return events != null ? events.equals(category.events) : category.events == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (events != null ? events.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Category{" +
            "id=" + id +
            ", name=" + name +
            ", events=" + events +
            '}';
  }
}
