package fr.knowledge.infra.repositories;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.common.Mapper;
import fr.knowledge.infra.models.CategoryElastic;
import fr.knowledge.infra.models.KnowledgeElastic;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
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
  public void should_create_element_in_elasticsearch() {
    CategoryElastic category = new CategoryElastic("aaa", "Architecture", Collections.singletonList(new KnowledgeElastic("kaa", "john@doe.fr", "My knowledge", "My content")));

    String element = Mapper.fromObjectToJsonString(category);

    elasticSearchService.createElement(ElasticIndexes.library.name(), element);
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
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.library.name(), "name", "SOLID");

    List<SearchResult.Hit<MockCategory, Void>> hits = searchResult.getHits(MockCategory.class);

    List<MockCategory> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(new MockCategory("aaa", "SOLID"));
  }

  @Test
  public void should_find_elements_with_wildcard() {
    SearchResult searchResult = elasticSearchService.searchElementsWilcard(ElasticIndexes.library.name(), "knowledges.title", "*knowledge");

    List<SearchResult.Hit<CategoryElastic, Void>> hits = searchResult.getHits(CategoryElastic.class);

    List<CategoryElastic> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(new CategoryElastic("aaa", "Architecture", Collections.singletonList(new KnowledgeElastic("kaa", "john@doe.fr", "My knowledge", "My content"))));
  }

  @Test
  public void should_update_element() {
    Map<String, String> updates = new HashMap<>();
    updates.put("name", "Architecture");
    elasticSearchService.updateElement(ElasticIndexes.library.name(), "aaa", updates);
  }
}
