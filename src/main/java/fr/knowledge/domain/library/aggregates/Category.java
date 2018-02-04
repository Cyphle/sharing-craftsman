package fr.knowledge.domain.library.aggregates;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.*;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CreateCategoryException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import fr.knowledge.domain.library.valueobjects.Name;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
  private Id id;
  private Name name;
  private final Map<Id, Knowledge> knowledges;
  private List<DomainEvent> changes;

  private Category(Id id, Name name) {
    this.id = id;
    this.name = name;
    knowledges = new HashMap<>();
    changes = new ArrayList<>();
  }

  private Category(Id id, Name name, Map<Id, Knowledge> knowledges) {
    this.id = id;
    this.name = name;
    this.knowledges = knowledges;
    changes = new ArrayList<>();
  }

  public Name getName() {
    return name;
  }

  public boolean is(Id id) {
    return this.id.equals(id);
  }

  public void update(Name newName) throws UpdateCategoryException {
    verifyCategory(newName);
    CategoryUpdatedEvent event = new CategoryUpdatedEvent(id, newName);
    apply(event);
  }

  public void delete() {
    CategoryDeletedEvent event = new CategoryDeletedEvent(id);
    apply(event);
  }

  public void addKnowledge(Knowledge knowledge) throws AddKnowledgeException {
    verifyKnowledge(knowledge);
    KnowledgeAddedEvent event = new KnowledgeAddedEvent(id, knowledge);
    apply(event);
  }

  public void updateKnowledge(Knowledge knowledge) throws AddKnowledgeException, KnowledgeNotFoundException {
    verifyKnowledge(knowledge);
    KnowledgeUpdatedEvent event = new KnowledgeUpdatedEvent(id, knowledge);
    apply(event);
  }

  public void deleteKnowledge(Id knowledgeId) throws KnowledgeNotFoundException {
    KnowledgeDeletedEvent event = new KnowledgeDeletedEvent(id, knowledgeId);
    apply(event);
  }

  public void apply(CategoryDeletedEvent event) {
    saveChanges(event);
  }

  public void apply(CategoryCreatedEvent categoryCreatedEvent) {
    id = categoryCreatedEvent.getId();
    name = categoryCreatedEvent.getName();
    saveChanges(categoryCreatedEvent);
  }

  public void apply(CategoryUpdatedEvent event) {
    name = event.getNewName();
    saveChanges(event);
  }

  public void apply(KnowledgeAddedEvent event) {
    knowledges.put(event.getKnowledgeId(), event.getKnowledge());
    saveChanges(event);
  }

  public void apply(KnowledgeUpdatedEvent event) throws KnowledgeNotFoundException {
    Knowledge knowledgeToUpdate = knowledges.get(event.getKnowledgeId());

    if (knowledgeToUpdate == null)
      throw new KnowledgeNotFoundException();

    knowledgeToUpdate.update(event.getKnowledge());
    saveChanges(event);
  }

  public void apply(KnowledgeDeletedEvent event) throws KnowledgeNotFoundException {
    Knowledge knowledgeToUpdate = knowledges.get(event.getKnowledgeId());

    if (knowledgeToUpdate == null)
      throw new KnowledgeNotFoundException();

    knowledges.remove(event.getKnowledgeId());
    saveChanges(event);
  }

  public List<DomainEvent> getChanges() {
    return changes;
  }

  public void saveChanges(DomainEvent event) {
    changes.add(event);
  }

  public void resetChanges() {
    changes = new ArrayList<>();
  }

  private void verifyKnowledge(Knowledge knowledge) throws AddKnowledgeException {
    if (knowledge.hasEmptyCreator())
      throw new AddKnowledgeException("Creator cannot be empty.");

    if (knowledge.hasEmptyTitle())
      throw new AddKnowledgeException("Title cannot be empty.");

    if (knowledge.hasEmptyContent())
      throw new AddKnowledgeException("Content cannot be empty.");
  }

  private void verifyCategory(Name name) throws UpdateCategoryException {
    if (name.isEmpty())
      throw new UpdateCategoryException("Name cannot be empty.");
  }

  public static Category of(String id, String name) {
    return new Category(Id.of(id), Name.of(name));
  }

  public static Category of(String id, String name, Map<Id, Knowledge> knowledges) {
    return new Category(Id.of(id), Name.of(name), knowledges);
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
    if (knowledges != null ? !knowledges.equals(category.knowledges) : category.knowledges != null) return false;
    return changes != null ? changes.equals(category.changes) : category.changes == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (knowledges != null ? knowledges.hashCode() : 0);
    result = 31 * result + (changes != null ? changes.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Category{" +
            "id=" + id +
            ", name=" + name +
            ", knowledges=" + knowledges +
            ", changes=" + changes +
            '}';
  }
}
