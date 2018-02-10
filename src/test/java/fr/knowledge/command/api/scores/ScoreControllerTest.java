package fr.knowledge.command.api.scores;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.scores.valueobjects.Mark;
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
@WebMvcTest(ScoreController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ScoreControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private ScoreService scoreService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_add_score_to_category() throws Exception {
    given(scoreService.addScore(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            ScoreDTO.from("john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", Mark.FOUR.value), "john@doe.fr")
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(post("/scores")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(ScoreDTO.from("john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", Mark.FOUR.value))))
            .andExpect(status().isOk());
  }

  @Test
  public void should_update_score() throws Exception {
    given(scoreService.addScore(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            ScoreDTO.from("aaa", "john@doe.fr", Mark.TWO.value), "john@doe.fr")
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(put("/scores")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(ScoreDTO.from("aaa", "john@doe.fr", Mark.TWO.value))))
            .andExpect(status().isOk());
  }

  @Test
  public void should_delete_score() throws Exception {
    given(scoreService.addScore(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            ScoreDTO.from("aaa", "john@doe.fr"), "john@doe.fr")
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(delete("/scores")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(ScoreDTO.from("aaa", "john@doe.fr"))))
            .andExpect(status().isOk());
  }
}
