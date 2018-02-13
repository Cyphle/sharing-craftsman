package fr.knowledge.query.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.SearchCriteria;
import fr.knowledge.query.queries.library.FindAllCategoriesQuery;
import fr.knowledge.query.queries.library.FindOneCategoryQuery;
import fr.knowledge.query.queries.library.SearchCategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryCategoryService {
  private final AuthorizationService authorizationService;
  private final QueryBus queryBus;

  @Autowired
  public QueryCategoryService(AuthorizationService authorizationService, QueryBus queryBus) {
    this.authorizationService = authorizationService;
    this.queryBus = queryBus;
  }

  public ResponseEntity getAllCategories(AuthorizationInfoDTO authorizationInfoDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindAllCategoriesQuery query = new FindAllCategoriesQuery();
    return ResponseEntity.ok(queryBus.send(query));
  }

  public ResponseEntity getCategoryById(AuthorizationInfoDTO authorizationInfoDTO, String categoryId) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    FindOneCategoryQuery query = new FindOneCategoryQuery(categoryId);
    List categories = queryBus.send(query);
    if (categories.isEmpty())
      return new ResponseEntity<>("No category for given id", HttpStatus.NO_CONTENT);
    return ResponseEntity.ok(categories.get(0));
  }

  public ResponseEntity searchCategories(AuthorizationInfoDTO authorizationInfoDTO, SearchCriteriaDTO searchCriteriaDTO) {
    if (!authorizationService.isUserAuthorized(authorizationInfoDTO))
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    SearchCriteria criteria = searchCriteriaDTO.fromApiToQuery();
    SearchCategoryQuery query = new SearchCategoryQuery(criteria);
    return ResponseEntity.ok(queryBus.send(query));
  }
}
