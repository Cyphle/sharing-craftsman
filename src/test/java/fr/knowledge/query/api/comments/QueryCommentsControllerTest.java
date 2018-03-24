package fr.knowledge.query.api.comments;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.infra.models.comments.CommentElastic;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(QueryCommentsController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class QueryCommentsControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private QueryCommentService commentService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_get_comments_for_a_content() throws Exception {
    given(commentService.getCommentsForContent(
            "aaa"
    )).willReturn(ResponseEntity.ok(Collections.singletonList(CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "My comment"))));

    this.mvc.perform(get("/comments/contentId/aaa")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is("caa")))
            .andExpect(jsonPath("$[0].commenter", is("john@doe.fr")))
            .andExpect(jsonPath("$[0].contentType", is("KNOWLEDGE")))
            .andExpect(jsonPath("$[0].contentId", is("aaa")))
            .andExpect(jsonPath("$[0].content", is("My comment")));
  }

  @Test
  public void should_get_comment_by_id() throws Exception {
    given(commentService.getCommentById(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            "aaa"
    )).willReturn(ResponseEntity.ok(CommentElastic.of("caa", "john@doe.fr", ContentType.KNOWLEDGE.name(), "aaa", "My comment")));

    this.mvc.perform(get("/comments/aaa")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("caa")))
            .andExpect(jsonPath("$.commenter", is("john@doe.fr")))
            .andExpect(jsonPath("$.contentType", is("KNOWLEDGE")))
            .andExpect(jsonPath("$.contentId", is("aaa")))
            .andExpect(jsonPath("$.content", is("My comment")));
  }
}
