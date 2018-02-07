package fr.knowledge.infra.repositories;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.config.JestConfig;
import fr.knowledge.infra.repositories.ElasticSearchService;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
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
    elasticSearchService.createIndex("CATEGORY");
  }

  @Test
  public void should_crate_element_in_elasticsearch() {
    String element = "{\n" +
            "  \"id\": \"aaa\",\n" +
            "  \"name\": \"SOLID\"\n" +
            "}";
    elasticSearchService.createElement(element);
  }

  @Test
  public void should_delete_element() {
    elasticSearchService.deleteElement("aaa", "CATEGORY");
  }

  @Test
  public void should_delete_index() {
    elasticSearchService.deleteIndex();
  }

  @Test
  public void should_find_elements() {
    SearchResult searchResult = elasticSearchService.searchElements("CATEGORY", "name", "SOLID");

    List<SearchResult.Hit<MockCategory, Void>> hits = searchResult.getHits(MockCategory.class);

    List<MockCategory> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(new MockCategory("aaa", "SOLID"));
  }

  @Test
  public void should_update_element() throws Exception {
    Map<String, String> updates = new HashMap<>();
    updates.put("name", "SOLID");
    elasticSearchService.updateElement("CATEGORY", "aaa", updates);
  }
}
