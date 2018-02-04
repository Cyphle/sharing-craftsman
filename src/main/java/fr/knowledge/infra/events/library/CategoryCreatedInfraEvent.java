package fr.knowledge.infra.events.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryCreatedEvent;
import fr.knowledge.domain.library.valueobjects.Name;
import fr.knowledge.infra.events.InfraEvent;

public class CategoryCreatedInfraEvent implements InfraEvent {
  private String id;
  private String name;

  public CategoryCreatedInfraEvent() {
  }

  public CategoryCreatedInfraEvent(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CategoryCreatedEvent fromInfraToDomain() {
    return new CategoryCreatedEvent(Id.of(id), Name.of(name));
  }

  public static CategoryCreatedInfraEvent fromDomainToInfra(DomainEvent domainEvent) {
    return new CategoryCreatedInfraEvent(domainEvent.getAggregateId(), ((CategoryCreatedEvent) domainEvent).getNameContent());
  }
}
