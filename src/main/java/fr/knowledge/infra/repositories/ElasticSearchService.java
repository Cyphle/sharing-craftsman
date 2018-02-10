package fr.knowledge.infra.repositories;

import fr.knowledge.config.JestConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class ElasticSearchService {
  protected final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private JestConfig jestConfig;

  public void createIndex(String type) {
    try {
      File file = new ClassPathResource("mapping/CategoryMapping.json").getFile();
      String mapping = FileUtils.readFileToString(file);

      CreateIndex index = new CreateIndex.Builder(jestConfig.getIndexName()).build();
      jestConfig.getClient().execute(index);

      PutMapping putMapping = new PutMapping.Builder(
              jestConfig.getIndexName(),
              type,
              mapping
      ).build();
      jestConfig.getClient().execute(putMapping);
    } catch (IOException e) {
      log.error("[ElasticSearchService::createIndex] Read mapping: " + e.getMessage());
    }
  }

  public void deleteIndex() {
    try {
      jestConfig.getClient().execute(new DeleteIndex.Builder(jestConfig.getIndexName()).build());
    } catch (IOException e) {
      log.error("[ElasticSearchService::deleteIndex] Cannot delete index: " + e.getMessage());
    }
  }

  public void createElement(String element) {
    try {
      Index doc = new Index.Builder(element).index("library").type("CATEGORY").build();
      jestConfig.getClient().execute(doc);
    } catch (IOException e) {
      log.error("[ElasticSearchService::createElement] Cannot insert element: " + e.getMessage());
    }
  }

  public void deleteElement(String id, String type) {
    try {
      jestConfig.getClient().execute(new Delete.Builder(id)
              .index(jestConfig.getIndexName())
              .type(type)
              .build());
    } catch (IOException e) {
      log.error("[ElasticSearchService::deleteElement] Cannot insert element: " + e.getMessage());
    }
  }

  public void updateElement(String type, String id, Map<String, String> updates) {
    try {
      StringJoiner keyValues = new StringJoiner(", ");
      updates.forEach((key, value) -> keyValues.add("\"" + key + "\": \"" + value + "\""));

      String script = "{" +
                "\"doc\" : {\n" +
                  keyValues.toString() +
                "}" +
              "}";

      jestConfig.getClient().execute(new Update.Builder(script).index(jestConfig.getIndexName()).type(type).id(id).build());
    } catch (IOException e) {
      log.error("[ElasticSearchService::updateElement] Cannot find element: " + e.getMessage());
    }
  }

  public SearchResult searchElements(String type, String property, String searchTerm) {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchQuery(type + "." + property, searchTerm));

    Search search = new Search.Builder(searchSourceBuilder.toString())
            .addIndex(jestConfig.getIndexName())
            .addType(type)
            .build();

    try {
      return jestConfig.getClient().execute(search);
    } catch (IOException e) {
      log.error("[ElasticSearchService::searchElements] Cannot find element: " + e.getMessage());
    }
    return null;
  }
}
