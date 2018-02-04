package fr.knowledge.infra.repositories;

import fr.knowledge.infra.models.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventStore extends CrudRepository<EventEntity, Long> {
  @Query("Select e from EventEntity e where e.aggreggateId = ?1")
  List<EventEntity> findByAggregateId(String aggregateId);
}
