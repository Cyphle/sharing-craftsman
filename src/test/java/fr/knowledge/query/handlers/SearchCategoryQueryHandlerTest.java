package fr.knowledge.query.handlers;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import fr.knowledge.query.queries.SearchCategoryQuery;
import fr.knowledge.query.queries.SearchCriteria;
import fr.knowledge.query.queries.SearchKey;
import fr.knowledge.query.services.CategoryQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class SearchCategoryQueryHandlerTest {
  private SearchCategoryQueryHandler searchCategoryQueryHandler;
  @Mock
  private CategoryQueryService categoryQueryService;

  @Before
  public void setUp() {
    given(categoryQueryService.search(any(SearchCriteria.class))).willReturn(Arrays.asList(
            CategoryElastic.of("d896903d-f9c2-4d60-a10d-9c4bbeb2392d", "SOLID"),
            CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    ));
    searchCategoryQueryHandler = new SearchCategoryQueryHandler(categoryQueryService);
  }

  @Test
  public void should_search_for_a_category_by_name() {
    SearchCriteria searchCriteria = new SearchCriteria();
    searchCriteria.with(SearchKey.CategoryName, "Architecture");
    searchCriteria.with(SearchKey.KnowledgeTitle, "know");
    SearchCategoryQuery query = new SearchCategoryQuery(searchCriteria);

    List<CategoryElastic> categories = searchCategoryQueryHandler.handle(query);
    assertThat(categories).containsExactly(
            CategoryElastic.of("d896903d-f9c2-4d60-a10d-9c4bbeb2392d", "SOLID"),
            CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    );
  }
}
