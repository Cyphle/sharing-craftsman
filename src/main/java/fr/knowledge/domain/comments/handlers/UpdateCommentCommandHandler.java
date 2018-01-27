package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.UpdateCommentCommand;
import fr.knowledge.domain.comments.exceptions.CommentNotFoundException;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.Id;

public class UpdateCommentCommandHandler {
  private CommentRepository commentRepository;

  public UpdateCommentCommandHandler(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public void handle(UpdateCommentCommand command) throws CommentNotFoundException {
    Comment comment = commentRepository.get(Id.of(command.getId()))
            .orElseThrow(CommentNotFoundException::new);
    comment.update(Content.of(command.getContent()));
    commentRepository.save(comment);
  }
}
