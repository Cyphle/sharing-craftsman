package fr.knowledge.remote.elastic;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.config.JestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KnowledgeLibraryApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class JestConfigTest {
  @Autowired
  private JestConfig jestConfig;

  @Test
  public void should_create_index_and_data() throws Exception {
    // Create elasticsearch index
//    CreateIndex index = new CreateIndex.Builder("library").build();
//    jestClientService.getClient().execute(index);
//
//    PutMapping putMapping = new PutMapping.Builder(
//            "library",
//            "CATEGORY",
//            "{ \"CATEGORY\" : { \"properties\" : { \"name\" : {\"type\" : \"string\", \"index\" : \"not_analyzed\"} } } }"
//    ).build();
//    jestClientService.getClient().execute(putMapping);
//
//    String category = Mapper.fromObjectToJsonString(Category.of("aab", "SOLID"));
//    Index doc = new Index.Builder(category).index("library").type("CATEGORY").build();
//    jestClientService.getClient().execute(doc);


//    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//    searchSourceBuilder.query(QueryBuilders.matchQuery("_all", "Architecture"));
//
//    Search search = new Search.Builder(searchSourceBuilder.toString())
//            // multiple index or types can be added.
//            .addIndex("library")
//            .addType("CATEGORY")
//            .build();
//
//    SearchResult result = jestClientService.getClient().execute(search);
//
//    List<SearchResult.Hit<Category, Void>> hits = result.getHits(Category.class);
//
//    String toto = "test";

            /*
    Search search = new Search.Builder(query)
            // multiple index or types can be added.
            .addIndex("library")
            .addType("CATEGORY")
            .build();
            */
  }
}
