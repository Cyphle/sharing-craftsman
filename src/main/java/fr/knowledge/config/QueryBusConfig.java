package fr.knowledge.config;

import fr.knowledge.infra.repositories.ElasticSearchService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.handlers.comments.FindCommentsForContentQueryHandler;
import fr.knowledge.query.handlers.comments.FindOneCommentQueryHandler;
import fr.knowledge.query.handlers.favorites.FindSelectionsForUserQueryHandler;
import fr.knowledge.query.handlers.library.FindAllCategoriesQueryHandler;
import fr.knowledge.query.handlers.library.FindOneCategoryQueryHandler;
import fr.knowledge.query.handlers.library.SearchCategoryQueryHandler;
import fr.knowledge.query.handlers.scores.FindScoresByMarkQueryHandler;
import fr.knowledge.query.handlers.scores.FindScoresForContentQueryHandler;
import fr.knowledge.query.queries.comments.FindCommentsForContentQuery;
import fr.knowledge.query.queries.comments.FindOneCommentQuery;
import fr.knowledge.query.queries.favorites.FindSelectionForUserQuery;
import fr.knowledge.query.queries.library.FindAllCategoriesQuery;
import fr.knowledge.query.queries.library.FindOneCategoryQuery;
import fr.knowledge.query.queries.library.SearchCategoryQuery;
import fr.knowledge.query.queries.scores.FindScoresByMarkQuery;
import fr.knowledge.query.queries.scores.FindScoresForContentQuery;
import fr.knowledge.query.services.CategoryQueryService;
import fr.knowledge.query.services.CommentQueryService;
import fr.knowledge.query.services.ScoreQueryService;
import fr.knowledge.query.services.SelectionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryBusConfig {
  private QueryBus queryBus;
  private ElasticSearchService elasticSearchService;

  @Autowired
  public QueryBusConfig(QueryBus queryBus, ElasticSearchService elasticSearchService) {
    this.queryBus = queryBus;
    this.elasticSearchService = elasticSearchService;
  }

  public void configure() {
    // Comments
    CommentQueryService commentQueryService = new CommentQueryService(elasticSearchService);
    queryBus.subscribe(FindCommentsForContentQuery.class, new FindCommentsForContentQueryHandler(commentQueryService));
    queryBus.subscribe(FindOneCommentQuery.class, new FindOneCommentQueryHandler(commentQueryService));
    // Favorites
    SelectionQueryService selectionQueryService = new SelectionQueryService(elasticSearchService);
    queryBus.subscribe(FindSelectionForUserQuery.class, new FindSelectionsForUserQueryHandler(selectionQueryService));
    // Library
    CategoryQueryService categoryQueryService = new CategoryQueryService(elasticSearchService);
    queryBus.subscribe(FindAllCategoriesQuery.class, new FindAllCategoriesQueryHandler(categoryQueryService));
    queryBus.subscribe(FindOneCategoryQuery.class, new FindOneCategoryQueryHandler(categoryQueryService));
    queryBus.subscribe(SearchCategoryQuery.class, new SearchCategoryQueryHandler(categoryQueryService));
    // Scores
    ScoreQueryService scoreQueryService = new ScoreQueryService(elasticSearchService);
    queryBus.subscribe(FindScoresByMarkQuery.class, new FindScoresByMarkQueryHandler(scoreQueryService));
    queryBus.subscribe(FindScoresForContentQuery.class, new FindScoresForContentQueryHandler(scoreQueryService));
  }
}
