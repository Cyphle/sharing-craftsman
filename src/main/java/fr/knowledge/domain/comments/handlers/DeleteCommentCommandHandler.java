package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.DeleteCommentCommand;
import fr.knowledge.domain.comments.exceptions.CommentNotFoundException;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.valueobjects.Id;

public class DeleteCommentCommandHandler {
  private CommentRepository commentRepository;

  public DeleteCommentCommandHandler(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public void handle(DeleteCommentCommand command) throws CommentNotFoundException {
    Comment comment = commentRepository.get(Id.of(command.getId()))
            .orElseThrow(CommentNotFoundException::new);
    comment.delete();
    commentRepository.save(comment);
  }
}
