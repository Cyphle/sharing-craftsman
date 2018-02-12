package fr.knowledge.query.api.favorites;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.common.Mapper;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.infra.models.favorites.SelectionElastic;
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

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(QuerySelectionController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class QuerySelectionControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private QuerySelectionService querySelectionService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_get_favorites_for_a_content() throws Exception {
    given(querySelectionService.getSelectionsOfUser(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            "john@doe.fr"
    )).willReturn(ResponseEntity.ok(Collections.singletonList(SelectionElastic.of("saa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa"))));

    this.mvc.perform(post("/favorites/search")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(UsernameDTO.of("john@doe.fr"))))
            .andExpect(status().isOk())
            .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is("saa")))
            .andExpect(jsonPath("$[0].username", is("john@doe.fr")))
            .andExpect(jsonPath("$[0].contentType", is("KNOWLEDGE")))
            .andExpect(jsonPath("$[0].contentId", is("aaa")));
  }
}
