package fr.knowledge.query.api.library;

import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.common.AuthorizationService;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import fr.knowledge.query.bus.QueryBus;
import fr.knowledge.query.queries.SearchCriteria;
import fr.knowledge.query.queries.SearchKey;
import fr.knowledge.query.queries.library.FindAllCategoriesQuery;
import fr.knowledge.query.queries.library.FindOneCategoryQuery;
import fr.knowledge.query.queries.library.SearchCategoryQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueryCategoryServiceTest {
  @Mock
  private QueryBus queryBus;
  @Mock
  private AuthorizationService authorizationService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  private QueryCategoryService queryCategoryService;

  @Before
  public void setUp() {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);
    given(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).willReturn(true);
    given(queryBus.send(new FindAllCategoriesQuery())).willReturn(Arrays.asList(
            CategoryElastic.of("aaa", "Architecture"),
            CategoryElastic.of("bbb", "SOLID", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    ));

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );

    queryCategoryService = new QueryCategoryService(authorizationService, queryBus);
  }

  @Test
  public void should_get_all_categories() {
    ResponseEntity response = queryCategoryService.getAllCategories();

    FindAllCategoriesQuery query = new FindAllCategoriesQuery();
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(Arrays.asList(
            CategoryElastic.of("aaa", "Architecture"),
            CategoryElastic.of("bbb", "SOLID", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    ));
  }

  @Test
  public void should_get_category_by_id() {
    given(queryBus.send(new FindOneCategoryQuery("aaa"))).willReturn(Collections.singletonList(
            CategoryElastic.of("aaa", "Architecture")
    ));

    ResponseEntity response = queryCategoryService.getCategoryById(authorizationInfoDTO, "aaa");

    FindOneCategoryQuery query = new FindOneCategoryQuery("aaa");
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(CategoryElastic.of("aaa", "Architecture"));
  }

  @Test
  public void should_search_for_categories() {
    SearchCriteria searchCriteria = new SearchCriteria();
    searchCriteria.with(SearchKey.CategoryName, "SO");
    searchCriteria.with(SearchKey.KnowledgeTitle, "know");
    given(queryBus.send(new SearchCategoryQuery(searchCriteria))).willReturn(Collections.singletonList(
            CategoryElastic.of("bbb", "SOLID", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    ));

    Map<String, String> searchKeys = new HashMap<>();
    searchKeys.put("CategoryName", "SO");
    searchKeys.put("KnowledgeTitle", "know");
    SearchCriteriaDTO searchCriteriaDTO = new SearchCriteriaDTO(searchKeys);
    ResponseEntity response = queryCategoryService.searchCategories(authorizationInfoDTO, searchCriteriaDTO);

    SearchCategoryQuery query = new SearchCategoryQuery(searchCriteria);
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(Collections.singletonList(
            CategoryElastic.of("bbb", "SOLID", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    ));
  }
}
