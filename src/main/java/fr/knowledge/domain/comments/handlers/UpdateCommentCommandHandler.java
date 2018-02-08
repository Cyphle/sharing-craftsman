package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.UpdateCommentCommand;
import fr.knowledge.domain.comments.exceptions.CommentNotFoundException;
import fr.knowledge.domain.comments.exceptions.CommentException;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

class UpdateCommentCommandHandler implements CommandHandler {
  private final CommentRepository commentRepository;

  public UpdateCommentCommandHandler(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Override
  public void handle(DomainCommand command) throws CommentNotFoundException, CommentException {
    Comment comment = commentRepository.get(Id.of(((UpdateCommentCommand) command).getId()))
            .orElseThrow(CommentNotFoundException::new);
    comment.update(Username.from(((UpdateCommentCommand) command).getCommenter()), Content.of(((UpdateCommentCommand) command).getContent()));
    commentRepository.save(comment);
  }
}
