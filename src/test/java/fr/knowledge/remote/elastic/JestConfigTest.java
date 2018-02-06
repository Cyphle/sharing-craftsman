package fr.knowledge.remote.elastic;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.common.Mapper;
import fr.knowledge.config.JestConfig;
import fr.knowledge.domain.library.aggregates.Category;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KnowledgeLibraryApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class JestConfigTest {
  @Autowired
  private JestConfig jestConfig;

  @Test
  public void should_create_index_and_data() throws Exception {
    // Create elasticsearch index
    CreateIndex index = new CreateIndex.Builder("library").build();
    jestConfig.getClient().execute(index);

    PutMapping putMapping = new PutMapping.Builder(
            "library",
            "CATEGORY",
            "{\n" +
                    "  \"_id\": {\n" +
                    "    \"path\": \"id\"\n" +
                    "  },\n" +
                    "  \"dynamic\": false,\n" +
                    "  \"properties\": {\n" +
                    "    \"id\": {\n" +
                    "      \"type\": \"string\",\n" +
                    "      \"index\": \"not_analyzed\"\n" +
                    "    },\n" +
                    "    \"name\": {\n" +
                    "      \"type\": \"string\",\n" +
                    "      \"index\": \"not_analyzed\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}"
    ).build();
    jestConfig.getClient().execute(putMapping);
//

      // --> CREATE ELEMENT
//    String category = Mapper.fromObjectToJsonString(Category.of("aab", "SOLID")); --> DESCENDRE D UN NIVEAU
//    Index doc = new Index.Builder("{\n" +
//            "  \"id\": \"aaa\",\n" +
//            "  \"name\": \"SOLID\"\n" +
//            "}").index("library").type("CATEGORY").build();
//    jestConfig.getClient().execute(doc);

//    operations.createIndex("library");


//    --> SEARCH
//    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//    searchSourceBuilder.query(QueryBuilders.matchQuery("CATEGORY.name", "SOLID"));
//
//    Search search = new Search.Builder(searchSourceBuilder.toString())
//            // multiple index or types can be added.
//            .addIndex("library")
//            .addType("CATEGORY")
//            .build();
//
//    SearchResult result = jestConfig.getClient().execute(search);
//
//    List<SearchResult.Hit<Category, Void>> hits = result.getHits(Category.class);
//
//    String toto = "test";

  }
}
