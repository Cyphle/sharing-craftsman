package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.DeleteCommentCommand;
import fr.knowledge.domain.comments.exceptions.CommentException;
import fr.knowledge.domain.comments.exceptions.CommentNotFoundException;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

public class DeleteCommentCommandHandler implements CommandHandler {
  private final CommentRepository commentRepository;

  public DeleteCommentCommandHandler(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Override
  public void handle(DomainCommand command) throws CommentNotFoundException, CommentException {
    Comment comment = commentRepository.get(Id.of(((DeleteCommentCommand) command).getId()))
            .orElseThrow(CommentNotFoundException::new);
    comment.delete(Username.from(((DeleteCommentCommand) command).getCommenter()));
    commentRepository.save(comment);
  }
}
