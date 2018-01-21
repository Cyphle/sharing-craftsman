package fr.knowledge.domain.library.aggregates;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.*;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import fr.knowledge.domain.library.valueobjects.Name;

import java.util.ArrayList;
import java.util.List;

public class Category {
  private final Id id;
  private Name name;
  private final List<Knowledge> knowledges;
  private final List<DomainEvent> changes;

  private Category(Id id, Name name) {
    this.id = id;
    this.name = name;
    knowledges = new ArrayList<>();
    changes = new ArrayList<>();
  }

  public Name getName() {
    return name;
  }

  public void update(Name newName) throws CreateCategoryException {
    verifyCategory(newName);
    CategoryUpdatedEvent event = new CategoryUpdatedEvent(id, newName);
    apply(event);
  }

  public void addKnowledge(Knowledge knowledge) throws AddKnowledgeException {
    verifyKnowledge(knowledge);
    AddedKnowledgeEvent event = new AddedKnowledgeEvent(id, knowledge);
    apply(event);
  }

  public void apply(AddedKnowledgeEvent event) {
    knowledges.add(event.getKnowledge());
    saveChanges(event);
  }

  private void apply(CategoryUpdatedEvent event) {
    name = event.getNewName();
    saveChanges(event);
  }

  public void saveChanges(DomainEvent event) {
    changes.add(event);
  }

  private void verifyKnowledge(Knowledge knowledge) throws AddKnowledgeException {
    if (knowledge.hasEmptyCreator())
      throw new AddKnowledgeException("Creator cannot be empty.");

    if (knowledge.hasEmptyTitle())
      throw new AddKnowledgeException("Title cannot be empty.");

    if (knowledge.hasEmptyContent())
      throw new AddKnowledgeException("Content cannot be empty.");
  }

  private void verifyCategory(Name name) throws CreateCategoryException {
    if (name.isEmpty())
      throw new CreateCategoryException("Name cannot be empty.");
  }

  public static Category of(String id, String name) {
    return new Category(Id.of(id), Name.of(name));
  }

  public static Category newCategory(String id, String name) throws CreateCategoryException {
    verifyCategory(name);
    Category category = Category.of(id, name);
    category.saveChanges(new CategoryCreatedEvent(Id.of(id), Name.of(name)));
    return category;
  }

  private static void verifyCategory(String name) throws CreateCategoryException {
    if (name.isEmpty())
      throw new CreateCategoryException("Name cannot be empty.");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Category category = (Category) o;

    if (id != null ? !id.equals(category.id) : category.id != null) return false;
    if (name != null ? !name.equals(category.name) : category.name != null) return false;
    return changes != null ? changes.equals(category.changes) : category.changes == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (changes != null ? changes.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Category{" +
            "id=" + id +
            ", name=" + name +
            ", changes=" + changes +
            '}';
  }
}
