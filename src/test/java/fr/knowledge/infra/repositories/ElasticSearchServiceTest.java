package fr.knowledge.infra.repositories;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.common.Mapper;
import fr.knowledge.infra.models.library.CategoryElastic;
import fr.knowledge.infra.models.library.KnowledgeElastic;
import io.searchbox.core.SearchResult;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
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
  public void should_create_element_in_elasticsearch() {
    CategoryElastic category = CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")));

    String element = Mapper.fromObjectToJsonString(category);

    elasticSearchService.createElement(ElasticIndexes.library.name(), element);
  }

  @Test
  public void should_delete_element() {
    elasticSearchService.deleteElement("library", "aaa");
  }

  @Test
  public void should_delete_index() {
    elasticSearchService.deleteIndex("library");
  }

  @Test
  public void should_find_elements() {
    SearchResult searchResult = elasticSearchService.searchElementsMatch(ElasticIndexes.library.name(), "name", "SOLID");

    List<SearchResult.Hit<CategoryElastic, Void>> hits = searchResult.getHits(CategoryElastic.class);

    List<CategoryElastic> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(CategoryElastic.of("d896903d-f9c2-4d60-a10d-9c4bbeb2392d", "SOLID"));
  }

  @Test
  public void should_find_elements_with_or_search_criteria() {
    Map<String, String> criterias = new HashMap<>();
    criterias.put("LIBRARY.name", "SOLID");
    criterias.put("LIBRARY.knowledges.title", "know");
    SearchResult searchResult = elasticSearchService.orCriteriaSearchElements(ElasticIndexes.library.name(), criterias);

    List<SearchResult.Hit<CategoryElastic, Void>> hits = searchResult.getHits(CategoryElastic.class);

    List<CategoryElastic> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(
            CategoryElastic.of("d896903d-f9c2-4d60-a10d-9c4bbeb2392d", "SOLID"),
            CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    );
  }

  @Test
  public void should_find_elements_with_and_search_criteria() {
    Map<String, String> criterias = new HashMap<>();
    criterias.put("name", "Arc");
    criterias.put("knowledges.title", "know");
    SearchResult searchResult = elasticSearchService.andCriteriaSearchElements(ElasticIndexes.library.name(), criterias);

    List<SearchResult.Hit<CategoryElastic, Void>> hits = searchResult.getHits(CategoryElastic.class);

    List<CategoryElastic> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(
            CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content")))
    );
  }

  @Test
  public void should_find_elements_with_wildcard() {
    SearchResult searchResult = elasticSearchService.searchElementsWildcard(ElasticIndexes.library.name(), "knowledges.title", "*knowledge");

    List<SearchResult.Hit<CategoryElastic, Void>> hits = searchResult.getHits(CategoryElastic.class);

    List<CategoryElastic> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(CategoryElastic.of("aaa", "Architecture", Collections.singletonList(KnowledgeElastic.of("kaa", "john@doe.fr", "My knowledge", "My content"))));
  }

  @Test
  public void should_find_all_elements_of_index() {
    SearchResult searchResult = elasticSearchService.findAllElements(ElasticIndexes.library.name());

    List<SearchResult.Hit<CategoryElastic, Void>> hits = searchResult.getHits(CategoryElastic.class);

    List<CategoryElastic> categories = hits.stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    assertThat(categories).containsExactly(CategoryElastic.of("d896903d-f9c2-4d60-a10d-9c4bbeb2392d", "SOLID"));
  }

  @Test
  public void should_update_element() {
    Map<String, String> updates = new HashMap<>();
    updates.put("name", "SOLID");
    elasticSearchService.updateElement(ElasticIndexes.library.name(), "aaa", updates);
  }

  @Test
  public void should_add_knowledge() {
    SearchResult searchResult = elasticSearchService.searchElementsWildcard(ElasticIndexes.library.name(), "knowledges.title", "*knowledge");

    List<CategoryElastic> categories = searchResult.getHits(CategoryElastic.class).stream()
            .map(h -> h.source)
            .collect(Collectors.toList());

    CategoryElastic category = categories.get(0);
    category.addKnowledge(KnowledgeElastic.of("kbb", "foo@bar", "Second knowledge", "Super content"));

    elasticSearchService.deleteElement(ElasticIndexes.library.name(), "aaa");
    elasticSearchService.createElement(ElasticIndexes.library.name(), Mapper.fromObjectToJsonString(category));
  }
}
