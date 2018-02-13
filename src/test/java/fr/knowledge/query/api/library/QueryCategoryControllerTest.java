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
@WebMvcTest(QueryCategoryController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class QueryCategoryControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private QueryCategoryService queryCategoryService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_get_all_categories() throws Exception {
    given(queryCategoryService.getAllCategories(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa")
    )).willReturn(ResponseEntity.ok(Arrays.asList(
            CategoryElastic.of("aaa", "Architecture"),
            CategoryElastic.of("bbb", "SOLID", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "Toto is tata")))
    )));

    this.mvc.perform(get("/library")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is("aaa")))
            .andExpect(jsonPath("$[0].name", is("Architecture")))
            .andExpect(jsonPath("$[0].knowledges", hasSize(0)))
            .andExpect(jsonPath("$[1].id", is("bbb")))
            .andExpect(jsonPath("$[1].name", is("SOLID")))
            .andExpect(jsonPath("$[1].knowledges", hasSize(1)))
            .andExpect(jsonPath("$[1].knowledges[0].id", is("kaa")))
            .andExpect(jsonPath("$[1].knowledges[0].creator", is("john@doe.fr")))
            .andExpect(jsonPath("$[1].knowledges[0].title", is("My knowledge")))
            .andExpect(jsonPath("$[1].knowledges[0].content", is("Toto is tata")));
  }

  @Test
  public void should_get_one_category_by_id() throws Exception {
    given(queryCategoryService.getCategoryById(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            "aaa"
    )).willReturn(ResponseEntity.ok(
            CategoryElastic.of("aaa", "Architecture")
    ));

    this.mvc.perform(get("/library/aaa")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("aaa")))
            .andExpect(jsonPath("$.name", is("Architecture")))
            .andExpect(jsonPath("$.knowledges", hasSize(0)));
  }

  @Test
  public void should_search_for_categories() throws Exception {
    Map<String, String> searchKeys = new HashMap<>();
    searchKeys.put("KnowledgeTitle", "Arch");
    searchKeys.put("KnowledgeTitle", "know");

    given(queryCategoryService.searchCategories(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            new SearchCriteriaDTO(searchKeys)
    )).willReturn(ResponseEntity.ok(Collections.singletonList(
            CategoryElastic.of("bbb", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "Toto is tata")))
    )));

    this.mvc.perform(post("/library/search")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(new SearchCriteriaDTO(searchKeys))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is("bbb")))
            .andExpect(jsonPath("$[0].name", is("Architecture")))
            .andExpect(jsonPath("$[0].knowledges", hasSize(1)))
            .andExpect(jsonPath("$[0].knowledges[0].id", is("kaa")))
            .andExpect(jsonPath("$[0].knowledges[0].creator", is("john@doe.fr")))
            .andExpect(jsonPath("$[0].knowledges[0].title", is("My knowledge")))
            .andExpect(jsonPath("$[0].knowledges[0].content", is("Toto is tata")));
  }
}
