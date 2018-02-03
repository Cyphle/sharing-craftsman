package fr.knowledge.infra.repositories;

import fr.knowledge.infra.models.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventStore extends CrudRepository<EventEntity, Long> {
}
