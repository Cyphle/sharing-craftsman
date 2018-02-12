package fr.knowledge.query.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.query.bus.QueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QueryCategoryService {
  private AuthorizationService authorizationService;
  private QueryBus queryBus;

  @Autowired
  public QueryCategoryService(AuthorizationService authorizationService, QueryBus queryBus) {
    this.authorizationService = authorizationService;
    this.queryBus = queryBus;
  }

  public ResponseEntity getAllCategories(AuthorizationInfoDTO authorizationInfoDTO) {
    return null;
  }

  public ResponseEntity getCategoryById(AuthorizationInfoDTO authorizationInfoDTO, String categoryId) {
    return null;
  }

  public ResponseEntity searchCategories(AuthorizationInfoDTO authorizationInfoDTO, SearchCriteriaDTO searchCriteriaDTO) {
    return null;
  }
}
