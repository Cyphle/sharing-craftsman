package fr.knowledge.query.handlers.favorites;

import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.infra.models.favorites.SelectionElastic;
import fr.knowledge.query.queries.favorites.FindSelectionForUserQuery;
import fr.knowledge.query.services.SelectionQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FindSelectionsForUserQueryHandlerTest {
  private FindSelectionsForUserQueryHandler findSelectionsForUserQueryHandler;
  @Mock
  private SelectionQueryService selectionQueryService;

  @Before
  public void setUp() {
    given(selectionQueryService.findSelectionForUser("john@doe.fr")).willReturn(Arrays.asList(
            SelectionElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa"),
            SelectionElastic.of("sbb", "john@doe.fr", ContentType.CATEGORY.name(), "bbb")
    ));
    findSelectionsForUserQueryHandler = new FindSelectionsForUserQueryHandler(selectionQueryService);
  }

  @Test
  public void should_find_scores_for_content() {
    FindSelectionForUserQuery query = new FindSelectionForUserQuery("john@doe.fr");

    List<SelectionElastic> scores = findSelectionsForUserQueryHandler.handle(query);
    assertThat(scores).containsExactly(
            SelectionElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa"),
            SelectionElastic.of("sbb", "john@doe.fr", ContentType.CATEGORY.name(), "bbb")
    );
  }
}
