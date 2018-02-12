package fr.knowledge.acceptance.config;

import fr.knowledge.KnowledgeLibraryApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(
        classes = {KnowledgeLibraryApplication.class},
        loader = SpringBootContextLoader.class)
@WebAppConfiguration
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SpringAcceptanceTestConfig {
  private final String BASE_URI = "http://localhost";
  private final int PORT = 8080;

  protected MockMvc mvc;
  protected MvcResult response;

  @Autowired
  protected WebApplicationContext context;

  protected String getBaseUri() {
    return BASE_URI + ":" + PORT;
  }
}
