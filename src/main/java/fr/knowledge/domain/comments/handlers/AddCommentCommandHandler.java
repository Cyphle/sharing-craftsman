package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.AddCommentCommand;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.CommandHandler;
import fr.knowledge.domain.common.DomainCommand;
import fr.knowledge.domain.common.utils.IdGenerator;

class AddCommentCommandHandler implements CommandHandler {
  private final IdGenerator idGenerator;
  private final CommentRepository commentRepository;

  public AddCommentCommandHandler(IdGenerator idGenerator, CommentRepository commentRepository) {
    this.idGenerator = idGenerator;
    this.commentRepository = commentRepository;
  }

  @Override
  public void handle(DomainCommand command) {
    Comment comment = Comment.newComment(
            idGenerator.generate(),
            ((AddCommentCommand) command).getCommenter(),
            ((AddCommentCommand) command).getContentType(),
            ((AddCommentCommand) command).getContentId(),
            ((AddCommentCommand) command).getContent()
    );
    commentRepository.save(comment);
  }
}
