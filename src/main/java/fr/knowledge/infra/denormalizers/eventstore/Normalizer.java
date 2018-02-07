package fr.knowledge.infra.denormalizers.eventstore;

import fr.knowledge.common.DateService;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.config.EventSourcingConfig;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.infra.models.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Normalizer {
  private IdGenerator idGenerator;
  private EventSourcingConfig eventSourcingConfig;
  private DateService dateTimeService;

  @Autowired
  public Normalizer(IdGenerator idGenerator, EventSourcingConfig eventSourcingConfig, DateService dateTimeService) {
    this.idGenerator = idGenerator;
    this.eventSourcingConfig = eventSourcingConfig;
    this.dateTimeService = dateTimeService;
  }

  public EventEntity normalize(DomainEvent change) {
    return new EventEntity(
            idGenerator.generate(),
            eventSourcingConfig.getVersion(),
            dateTimeService.nowInDate(),
            change.getAggregateId(),
            SerializerMapper.getInfraEventClass(change.getClass().getSimpleName()),
            SerializerMapper.serialize(change)
    );
  }
}
