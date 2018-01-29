package fr.knowledge.api.library;

import fr.knowledge.KnowledgeLibraryApplication;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KnowledgeLibraryApplication.class})
@WebMvcTest(CategoryController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CategoryControllerTest {
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
  public void should_create_new_category() throws Exception {
    given(libraryService.createCategory(CategoryDTO.from("Architecture"))).willReturn(ResponseEntity.ok().build());

    this.mvc.perform(post("/library/category")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Mapper.fromObjectToJsonString(CategoryDTO.from("Architecture"))))
            .andExpect(status().isOk());
  }
}
