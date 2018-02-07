package fr.knowledge.domain.comments.ports;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.common.valueobjects.Id;

import java.util.Optional;

public interface CommentRepository {
  Optional<Comment> get(Id aggregateId);

  void save(Comment comment);
}
