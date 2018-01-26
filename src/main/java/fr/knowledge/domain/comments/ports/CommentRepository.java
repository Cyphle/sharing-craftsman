package fr.knowledge.domain.comments.ports;

import fr.knowledge.domain.comments.aggregates.Comment;

public interface CommentRepository {
  void save(Comment comment);
}
