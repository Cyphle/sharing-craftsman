package fr.knowledge.query.api.scores;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.valueobjects.Mark;
import fr.knowledge.infra.models.scores.ScoreElastic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(QueryScoreController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class QueryScoreControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private QueryScoreService queryScoreService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_get_scores_of_content_by_id() throws Exception {
    given(queryScoreService.getScoresByContentId(
            "aaa"
    )).willReturn(ResponseEntity.ok(Arrays.asList(
            ScoreElastic.of("saa", "john@doe.fr", ContentType.CATEGORY.name(), "aaa", Mark.FOUR.value),
            ScoreElastic.of("sbb", "foo@bar.fr", ContentType.CATEGORY.name(), "aaa", Mark.THREE.value)
    )));

    this.mvc.perform(get("/scores/contentId/aaa")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is("saa")))
            .andExpect(jsonPath("$[0].giver", is("john@doe.fr")))
            .andExpect(jsonPath("$[0].contentType", is("CATEGORY")))
            .andExpect(jsonPath("$[0].contentId", is("aaa")))
            .andExpect(jsonPath("$[0].mark", is(4)))
            .andExpect(jsonPath("$[1].id", is("sbb")))
            .andExpect(jsonPath("$[1].giver", is("foo@bar.fr")))
            .andExpect(jsonPath("$[1].contentType", is("CATEGORY")))
            .andExpect(jsonPath("$[1].contentId", is("aaa")))
            .andExpect(jsonPath("$[1].mark", is(3)));
  }

  @Test
  public void should_get_scores_of_given_mark() throws Exception {
    given(queryScoreService.getScoresByMark(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            4
    )).willReturn(ResponseEntity.ok(Arrays.asList(
            ScoreElastic.of("saa", "john@doe.fr", ContentType.CATEGORY.name(), "aaa", Mark.FOUR.value),
            ScoreElastic.of("sbb", "foo@bar.fr", ContentType.KNOWLEDGE.name(), "bbb", Mark.FOUR.value)
    )));

    this.mvc.perform(get("/scores/mark/4")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$[0].id", is("saa")))
            .andExpect(jsonPath("$[0].giver", is("john@doe.fr")))
            .andExpect(jsonPath("$[0].contentType", is("CATEGORY")))
            .andExpect(jsonPath("$[0].contentId", is("aaa")))
            .andExpect(jsonPath("$[0].mark", is(4)))
            .andExpect(jsonPath("$[1].id", is("sbb")))
            .andExpect(jsonPath("$[1].giver", is("foo@bar.fr")))
            .andExpect(jsonPath("$[1].contentType", is("KNOWLEDGE")))
            .andExpect(jsonPath("$[1].contentId", is("bbb")))
            .andExpect(jsonPath("$[1].mark", is(4)));
  }
}
