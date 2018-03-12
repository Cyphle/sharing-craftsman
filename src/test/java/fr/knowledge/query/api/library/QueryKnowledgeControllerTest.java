package fr.knowledge.query.api.library;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.common.Mapper;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(QueryKnowledgeController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class QueryKnowledgeControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private QueryKnowledgeService queryKnowledgeService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_get_one_category_by_id() throws Exception {
    given(queryKnowledgeService.getKnowledgeById(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            "kaa"
    )).willReturn(ResponseEntity.ok(
            CategoryElastic.of(
                    "aaa",
                    "Architecture",
                    Collections.singletonList(
                            KnowledgeElastic.of("kaa", "john@doe.fr", "CQRS", "Command query segregation principle")
                    )
            )
    ));

    this.mvc.perform(get("/library/knowledges/kaa")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("aaa")))
            .andExpect(jsonPath("$.name", is("Architecture")))
            .andExpect(jsonPath("$.knowledges[0].id", is("kaa")))
            .andExpect(jsonPath("$.knowledges[0].creator", is("john@doe.fr")))
            .andExpect(jsonPath("$.knowledges[0].title", is("CQRS")))
            .andExpect(jsonPath("$.knowledges[0].content", is("Command query segregation principle")));
  }
}
