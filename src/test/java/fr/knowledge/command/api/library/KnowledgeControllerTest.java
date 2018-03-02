package fr.knowledge.command.api.library;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(KnowledgeController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class KnowledgeControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private LibraryService libraryService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_add_knowledge_to_category() throws Exception {
    given(libraryService.addKnowledge(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            KnowledgeDTO.from("aaa", "john@doe.fr", "title", "content"))
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(post("/library/knowledges")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(KnowledgeDTO.from("aaa", "john@doe.fr", "title", "content"))))
            .andExpect(status().isOk());
  }

  @Test
  public void should_update_knowledge() throws Exception {
    given(libraryService.updateKnowledge(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            KnowledgeDTO.from("aaa", "aaa", "john@doe.fr", "title", "content"))
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(put("/library/knowledges")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(KnowledgeDTO.from("aaa", "aaa",  "john@doe.fr", "title", "content"))))
            .andExpect(status().isOk());
  }

  @Test
  public void should_delete_knowledge() throws Exception {
    given(libraryService.deleteKnowledge(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            KnowledgeDTO.from("aaa", "aaa"))
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(post("/library/knowledges/delete")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(KnowledgeDTO.from("aaa", "aaa"))))
            .andExpect(status().isOk());
  }
}
