package fr.knowledge.domain.library.aggregates;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.*;
import fr.knowledge.domain.library.exceptions.AddKnowledgeException;
import fr.knowledge.domain.library.exceptions.CategoryException;
import fr.knowledge.domain.library.exceptions.KnowledgeNotFoundException;
import fr.knowledge.domain.library.valueobjects.Knowledge;
import fr.knowledge.domain.library.valueobjects.Name;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@EqualsAndHashCode
@ToString
public class Category {
  private Id id;
  private Name name;
  private Map<Id, Knowledge> knowledges;
  private List<DomainEvent> changes;
  private boolean deleted;

  public Category() { }

  private Category(Id id, Name name) throws CategoryException {
    verifyCategory(name);
    this.id = id;
    this.name = name;
    this.deleted = false;
    knowledges = new HashMap<>();
    changes = new ArrayList<>();
    CategoryCreatedEvent categoryCreatedEvent = new CategoryCreatedEvent(id, name);
    apply(categoryCreatedEvent);
    saveChanges(categoryCreatedEvent);
  }

  public Name getName() {
    return name;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void update(Name newName) throws CategoryException {
    verifyCategory(newName);
    CategoryUpdatedEvent event = new CategoryUpdatedEvent(id, newName);
    apply(event);
    saveChanges(event);
  }

  public void delete() {
    CategoryDeletedEvent event = new CategoryDeletedEvent(id);
    apply(event);
    saveChanges(event);
  }

  public void addKnowledge(Knowledge knowledge) throws AddKnowledgeException {
    verifyKnowledge(knowledge);
    KnowledgeAddedEvent event = new KnowledgeAddedEvent(id, knowledge);
    apply(event);
    saveChanges(event);
  }

  public void updateKnowledge(Knowledge knowledge) throws AddKnowledgeException, KnowledgeNotFoundException {
    Knowledge knowledgeToUpdate = knowledges.get(knowledge.getId());

    if (knowledgeToUpdate == null)
      throw new KnowledgeNotFoundException();

    verifyKnowledge(knowledge);
    KnowledgeUpdatedEvent event = new KnowledgeUpdatedEvent(id, knowledge);
    apply(event);
    saveChanges(event);
  }

  public void deleteKnowledge(Id knowledgeId) throws KnowledgeNotFoundException {
    Knowledge knowledgeToUpdate = knowledges.get(knowledgeId);

    if (knowledgeToUpdate == null)
      throw new KnowledgeNotFoundException();

    KnowledgeDeletedEvent event = new KnowledgeDeletedEvent(id, knowledgeId);
    apply(event);
    saveChanges(event);
  }

  public Category apply(CategoryCreatedEvent categoryCreatedEvent) {
    id = categoryCreatedEvent.getId();
    name = categoryCreatedEvent.getName();
    deleted = false;
    knowledges = new HashMap<>();
    changes = new ArrayList<>();
    return this;
  }

  public Category apply(CategoryDeletedEvent event) {
    deleted = true;
    return this;
  }

  public Category apply(CategoryUpdatedEvent event) {
    name = event.getNewName();
    return this;
  }

  public Category apply(KnowledgeAddedEvent event) {
    knowledges.put(event.getKnowledgeId(), event.getKnowledge());
    return this;
  }

  public Category apply(KnowledgeUpdatedEvent event) {
    knowledges.get(event.getKnowledgeId()).update(event.getKnowledge());
    return this;
  }

  public Category apply(KnowledgeDeletedEvent event) {
    knowledges.remove(event.getKnowledgeId());
    return this;
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

  private void verifyCategory(Name name) throws CategoryException {
    if (name.isEmpty())
      throw new CategoryException("Name cannot be empty.");
  }

  public static Category of(String id, String name) {
    Category category = new Category();
    category.apply(new CategoryCreatedEvent(Id.of(id), Name.of(name)));
    return category;
  }

  public static Category newCategory(String id, String name) throws CategoryException {
    return new Category(Id.of(id), Name.of(name));
  }

  public static Category rebuild(List<DomainEvent> events) {
    return events.stream()
            .reduce(new Category(),
                    (category, event) -> (Category) event.apply(category),
                    (category1, category2) -> category2);
  }
}
