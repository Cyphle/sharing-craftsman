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
import fr.knowledge.query.queries.library.FindOneKnowledgeQuery;
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
public class QueryKnowledgeServiceTest {
  @Mock
  private QueryBus queryBus;
  @Mock
  private AuthorizationService authorizationService;
  private AuthorizationInfoDTO authorizationInfoDTO;
  private QueryKnowledgeService queryKnowledgeService;

  @Before
  public void setUp() {
    given(authorizationService.isUserAuthorized(any(AuthorizationInfoDTO.class))).willReturn(true);
    given(authorizationService.areUsernameEquals("john@doe.fr", "john@doe.fr")).willReturn(true);

    authorizationInfoDTO = new AuthorizationInfoDTO(
            "client",
            "secret",
            "john@doe.fr",
            "token"
    );

    queryKnowledgeService = new QueryKnowledgeService(authorizationService, queryBus);
  }

  @Test
  public void should_get_knowledge_by_id() {
    given(queryBus.send(new FindOneKnowledgeQuery("kaa"))).willReturn(Collections.singletonList(
            CategoryElastic.of(
                    "aaa",
                    "Architecture",
                    Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "CQRS", "Command query responsibility principle"))
            )
    ));

    ResponseEntity response = queryKnowledgeService.getKnowledgeById(authorizationInfoDTO, "kaa");

    FindOneKnowledgeQuery query = new FindOneKnowledgeQuery("kaa");
    verify(queryBus).send(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(CategoryElastic.of(
            "aaa",
            "Architecture",
            Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "CQRS", "Command query responsibility principle"))
    ));
  }
}
