package fr.knowledge.command.api.favorites;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.common.Mapper;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(SelectionController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class SelectionControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private SelectionService selectionService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_add_knowledge_to_my_selection() throws Exception {
    given(selectionService.addSelection(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            SelectionDTO.from("john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa"),
            "john@doe.fr")
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(post("/favorites")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(SelectionDTO.from("john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa"))))
            .andExpect(status().isOk());
  }

  @Test
  public void should_remove_category_from_my_selection() throws Exception {
    given(selectionService.removeSelection(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            SelectionDTO.from("aaa", "john@doe.fr"),
            "john@doe.fr")
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(delete("/favorites")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(SelectionDTO.from("aaa", "john@doe.fr"))))
            .andExpect(status().isOk());
  }
}
