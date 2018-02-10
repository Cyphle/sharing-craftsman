package fr.knowledge.domain.common;

public interface DomainEvent<T> {
  String getAggregateId();

  T apply(T aggregate);
}
