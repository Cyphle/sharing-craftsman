package fr.knowledge.domain.comments.ports;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

import java.util.Optional;

public interface CommentRepository {
  Optional<Comment> get(Id id, Username from);

  void save(Comment comment);
}
