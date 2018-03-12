package fr.knowledge.query.handlers.library;

import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import fr.knowledge.query.queries.library.FindOneKnowledgeQuery;
import fr.knowledge.query.services.KnowledgeQueryService;
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

@RunWith(MockitoJUnitRunner.class)
public class FindOneKnowledgeQueryHandlerTest {
  private FindOneKnowledgeQueryHandler findOneKnowledgeQueryHandler;
  @Mock
  private KnowledgeQueryService knowledgeQueryService;

  @Before
  public void setUp() {
    given(knowledgeQueryService.findOneById("kaa")).willReturn(Collections.singletonList(CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "CQRS", "Command query segregation principle")))));
    findOneKnowledgeQueryHandler = new FindOneKnowledgeQueryHandler(knowledgeQueryService);
  }

  @Test
  public void should_find_one_category_by_id() {
    FindOneKnowledgeQuery query = new FindOneKnowledgeQuery("kaa");

    List<CategoryElastic> knowledge = findOneKnowledgeQueryHandler.handle(query);

    assertThat(knowledge).hasSize(1);
    assertThat(knowledge).containsExactly(CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "CQRS", "Command query segregation principle"))));
  }
}
