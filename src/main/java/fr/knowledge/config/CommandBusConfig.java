package fr.knowledge.config;

import fr.knowledge.command.bus.CommandBus;
import fr.knowledge.common.IdGenerator;
import fr.knowledge.domain.comments.commands.AddCommentCommand;
import fr.knowledge.domain.comments.commands.DeleteCommentCommand;
import fr.knowledge.domain.comments.commands.UpdateCommentCommand;
import fr.knowledge.domain.comments.handlers.AddCommentCommandHandler;
import fr.knowledge.domain.comments.handlers.DeleteCommentCommandHandler;
import fr.knowledge.domain.comments.handlers.UpdateCommentCommandHandler;
import fr.knowledge.domain.comments.ports.CommentRepository;
import fr.knowledge.domain.favorites.commands.AddToMySelectionCommand;
import fr.knowledge.domain.favorites.commands.RemoveFromMySelectionCommand;
import fr.knowledge.domain.favorites.handlers.AddToMySelectionCommandHandler;
import fr.knowledge.domain.favorites.handlers.RemoveFromMySelectionCommandHandler;
import fr.knowledge.domain.favorites.ports.SelectionRepository;
import fr.knowledge.domain.library.commands.*;
import fr.knowledge.domain.library.handlers.*;
import fr.knowledge.domain.library.ports.CategoryRepository;
import fr.knowledge.domain.scores.commands.AddScoreCommand;
import fr.knowledge.domain.scores.commands.DeleteScoreCommand;
import fr.knowledge.domain.scores.commands.UpdateScoreCommand;
import fr.knowledge.domain.scores.handlers.AddScoreCommandHandler;
import fr.knowledge.domain.scores.handlers.DeleteScoreCommandHandler;
import fr.knowledge.domain.scores.handlers.UpdateScoreCommandHandler;
import fr.knowledge.domain.scores.ports.ScoreRepository;
import fr.knowledge.infra.adapters.comments.CommentAdapter;
import fr.knowledge.infra.adapters.favorites.SelectionAdapter;
import fr.knowledge.infra.adapters.library.CategoryAdapter;
import fr.knowledge.infra.adapters.scores.ScoreAdapter;
import fr.knowledge.infra.bus.EventBus;
import fr.knowledge.infra.denormalizers.Normalizer;
import fr.knowledge.infra.repositories.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandBusConfig {
  private CommandBus commandBus;
  private IdGenerator idGenerator;
  private EventBus eventBus;
  private EventStore eventStore;
  private Normalizer normalizer;

  @Autowired
  public CommandBusConfig(
          CommandBus commandBus,
          IdGenerator idGenerator,
          EventBus eventBus,
          EventStore eventStore,
          Normalizer normalizer) {
    this.commandBus = commandBus;
    this.idGenerator = idGenerator;
    this.eventBus = eventBus;
    this.eventStore = eventStore;
    this.normalizer = normalizer;
  }

  public void configure() {
    // Comments
    CommentRepository commentRepository = new CommentAdapter(eventBus, eventStore, normalizer);
    commandBus.subscribe(AddCommentCommand.class, new AddCommentCommandHandler(idGenerator, commentRepository));
    commandBus.subscribe(UpdateCommentCommand.class, new UpdateCommentCommandHandler(commentRepository));
    commandBus.subscribe(DeleteCommentCommand.class, new DeleteCommentCommandHandler(commentRepository));
    // Favorites
    SelectionRepository selectionRepository = new SelectionAdapter(eventBus, eventStore, normalizer);
    commandBus.subscribe(AddToMySelectionCommand.class, new AddToMySelectionCommandHandler(idGenerator, selectionRepository));
    commandBus.subscribe(RemoveFromMySelectionCommand.class, new RemoveFromMySelectionCommandHandler(selectionRepository));
    // Library
    CategoryRepository categoryRepository = new CategoryAdapter(eventBus, eventStore, normalizer);
    commandBus.subscribe(CreateCategoryCommand.class, new CreateCategoryCommandHandler(idGenerator, categoryRepository));
    commandBus.subscribe(UpdateCategoryCommand.class, new UpdateCategoryCommandHandler(categoryRepository));
    commandBus.subscribe(DeleteCategoryCommand.class, new DeleteCategoryCommandHandler(categoryRepository));
    commandBus.subscribe(AddKnowledgeCommand.class, new AddKnowledgeCommandHandler(idGenerator, categoryRepository));
    commandBus.subscribe(UpdateKnowledgeCommand.class, new UpdateKnowledgeCommandHandler(categoryRepository));
    commandBus.subscribe(DeleteKnowledgeCommand.class, new DeleteKnowledgeCommandHandler(categoryRepository));
    // Scores
    ScoreRepository scoreRepository = new ScoreAdapter(eventBus, eventStore, normalizer);
    commandBus.subscribe(AddScoreCommand.class, new AddScoreCommandHandler(idGenerator, scoreRepository));
    commandBus.subscribe(UpdateScoreCommand.class, new UpdateScoreCommandHandler(scoreRepository));
    commandBus.subscribe(DeleteScoreCommand.class, new DeleteScoreCommandHandler(scoreRepository));
  }
}
