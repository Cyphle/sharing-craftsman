package fr.knowledge.infra.events.library;

import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.library.events.CategoryDeletedEvent;

public class CategoryDeletedInfraEvent {
  private String id;

  public CategoryDeletedInfraEvent() {
  }

  public CategoryDeletedInfraEvent(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public DomainEvent fromInfraToDomain() {
    return new CategoryDeletedEvent(Id.of(id));
  }

  public static CategoryDeletedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new CategoryDeletedInfraEvent(event.getAggregateId());
  }
}
