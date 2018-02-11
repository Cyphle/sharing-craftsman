package fr.knowledge.query.handlers.favorites;

import fr.knowledge.infra.models.favorites.SelectionElastic;
import fr.knowledge.query.handlers.QueryHandler;
import fr.knowledge.query.queries.Query;
import fr.knowledge.query.queries.favorites.FindSelectionForUserQuery;
import fr.knowledge.query.services.SelectionQueryService;

import java.util.List;

public class FindSelectionsForUserQueryHandler implements QueryHandler<SelectionElastic> {
  private SelectionQueryService selectionQueryService;

  public FindSelectionsForUserQueryHandler(SelectionQueryService selectionQueryService) {
    this.selectionQueryService = selectionQueryService;
  }

  @Override
  public List<SelectionElastic> handle(Query query) {
    return selectionQueryService.findSelectionForUser(((FindSelectionForUserQuery) query).getUsername());
  }
}
