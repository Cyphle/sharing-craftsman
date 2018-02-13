package fr.knowledge.query.handlers.library;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import fr.knowledge.query.handlers.library.SearchCategoryQueryHandler;
import fr.knowledge.query.queries.library.SearchCategoryQuery;
import fr.knowledge.query.queries.SearchCriteria;
import fr.knowledge.query.queries.SearchKey;
import fr.knowledge.query.services.CategoryQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    given(categoryQueryService.search(any(SearchCriteria.class))).willReturn(Collections.singletonList(
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
            CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    );
  }
}
