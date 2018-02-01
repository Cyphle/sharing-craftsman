package fr.knowledge.command.api.comments;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.command.api.common.AuthorizationInfoDTO;
import fr.knowledge.command.api.favorites.SelectionDTO;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.utils.Mapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(CommentController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CommentControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private CommentService commentService;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void should_add_comment_to_category() throws Exception {
    given(commentService.addComment(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            CommentDTO.from("commenter", ContentType.CATEGORY.name(), "aaa", "content"))
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(post("/comments")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(CommentDTO.from("commenter", ContentType.CATEGORY.name(), "aaa", "content"))))
            .andExpect(status().isOk());
  }

  @Test
  public void should_update_comment() throws Exception {
    given(commentService.addComment(
            new AuthorizationInfoDTO("client", "clientsecret", "john@doe.fr", "aaa"),
            CommentDTO.from("commenter", ContentType.CATEGORY.name(), "aaa", "content"))
    ).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(put("/comments")
            .header("client", "client")
            .header("secret", "clientsecret")
            .header("username", "john@doe.fr")
            .header("access-token", "aaa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(CommentDTO.from("aaa", "commenter",  "content"))))
            .andExpect(status().isOk());
  }
}
