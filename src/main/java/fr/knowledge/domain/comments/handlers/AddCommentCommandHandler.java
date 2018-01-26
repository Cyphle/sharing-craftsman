package fr.knowledge.domain.comments.handlers;

import fr.knowledge.domain.comments.aggregates.Comment;
import fr.knowledge.domain.comments.commands.AddCommentCommand;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.common.utils.IdGenerator;

public class AddCommentCommandHandler {
  private final IdGenerator idGenerator;
  private final CommentRepository commentRepository;

  public AddCommentCommandHandler(IdGenerator idGenerator, CommentRepository commentRepository) {
    this.idGenerator = idGenerator;
    this.commentRepository = commentRepository;
  }

  public void handle(AddCommentCommand command) {
    Comment comment = Comment.newComment(idGenerator.generate(), command.getCommenter(), command.getContentType(), command.getContentId(), command.getContent());
    commentRepository.save(comment);
  }
}
