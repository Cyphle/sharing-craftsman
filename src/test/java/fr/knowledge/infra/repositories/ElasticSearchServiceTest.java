package fr.knowledge.infra.repositories;

import fr.knowledge.KnowledgeLibraryApplication;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KnowledgeLibraryApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class ElasticSearchServiceTest {
  @Autowired
  private ElasticSearchService elasticSearchService;

  @Test
  public void should_create_index_and_mapping() {
    elasticSearchService.createIndexes();
  }

  @Test
  public void should_crate_element_in_elasticsearch() {
    String element = "{\n" +
            "  \"id\": \"aaa\",\n" +
            "  \"name\": \"SOLID\"\n" +
            "}";
    elasticSearchService.createElement("library", element);
  }

  @Test
  public void should_delete_element() {
    elasticSearchService.deleteElement("aaa", "library");
  }

  @Test
  public void should_delete_index() {
    elasticSearchService.deleteIndex("library");
  }

  @Test
  public void should_find_elements() {
    SearchResult searchResult = elasticSearchService.searchElements(ElasticIndexes.library.name(), "name", "SOLID");

    List<SearchResult.Hit<MockCategory, Void>> hits = searchResult.getHits(MockCategory.class);

    List<MockCategory> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(new MockCategory("aaa", "SOLID"));
  }

  @Test
  public void should_update_element() {
    Map<String, String> updates = new HashMap<>();
    updates.put("name", "Architecture");
    elasticSearchService.updateElement(ElasticIndexes.library.name(), "aaa", updates);
  }
}
