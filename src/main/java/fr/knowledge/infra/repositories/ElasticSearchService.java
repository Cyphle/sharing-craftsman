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
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final JestConfig jestConfig;

  @Autowired
  public ElasticSearchService(JestConfig jestConfig) {
    this.jestConfig = jestConfig;
  }

  public void createIndexes() {
    jestConfig.getIndexNames().forEach((key, value) -> {
      try {
        File file = new ClassPathResource(value).getFile();
        String mapping = FileUtils.readFileToString(file);

        CreateIndex createIndex = new CreateIndex.Builder(key).build();
        jestConfig.getClient().execute(createIndex);

        PutMapping putMapping = new PutMapping.Builder(
                key,
                key.toUpperCase(),
                mapping
        ).build();
        jestConfig.getClient().execute(putMapping);
      } catch (IOException e) {
        log.error("[ElasticSearchService::createIndexes] Read mapping: " + e.getMessage());
      }
    });
  }

  public void deleteIndex(String index) {
    try {
      jestConfig.getClient().execute(new DeleteIndex.Builder(index).build());
    } catch (IOException e) {
      log.error("[ElasticSearchService::deleteIndex] Cannot delete index: " + e.getMessage());
    }
  }

  public void createElement(String index, String element) {
    try {
      Index doc = new Index.Builder(element).index(index).type(index.toUpperCase()).build();
      jestConfig.getClient().execute(doc);
    } catch (IOException e) {
      log.error("[ElasticSearchService::createElement] Cannot insert element: " + e.getMessage());
    }
  }

  public void deleteElement(String index, String id) {
    try {
      jestConfig.getClient().execute(new Delete.Builder(id)
              .index(index)
              .type(index.toUpperCase())
              .build());
    } catch (IOException e) {
      log.error("[ElasticSearchService::deleteElement] Cannot insert element: " + e.getMessage());
    }
  }

  public void updateElement(String index, String id, Map<String, String> updates) {
    try {
      StringJoiner keyValues = new StringJoiner(", ");
      updates.forEach((key, value) -> keyValues.add("\"" + key + "\": \"" + value + "\""));

      String script = "{" +
              "\"doc\" : {\n" +
              keyValues.toString() +
              "}" +
              "}";

      jestConfig.getClient().execute(new Update.Builder(script).index(index).type(index.toUpperCase()).id(id).build());
    } catch (IOException e) {
      log.error("[ElasticSearchService::updateElement] Cannot find element: " + e.getMessage());
    }
  }

  public SearchResult searchElementsMatch(String index, String property, String searchTerm) {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchQuery(index.toUpperCase() + "." + property, searchTerm));

    Search search = new Search.Builder(searchSourceBuilder.toString())
            .addIndex(index)
            .addType(index.toUpperCase())
            .build();

    try {
      return jestConfig.getClient().execute(search);
    } catch (IOException e) {
      log.error("[ElasticSearchService::searchElementsMatch] Cannot find element: " + e.getMessage());
    }
    return null;
  }

  public SearchResult searchElementsWildcard(String index, String property, String searchTerm) {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.wildcardQuery(index.toUpperCase() + "." + property, searchTerm));

    Search search = new Search.Builder(searchSourceBuilder.toString())
            .addIndex(index)
            .addType(index.toUpperCase())
            .build();

    try {
      return jestConfig.getClient().execute(search);
    } catch (IOException e) {
      log.error("[ElasticSearchService::searchElementsMatch] Cannot find element: " + e.getMessage());
    }
    return null;
  }

  public SearchResult orCriteriaSearchElements(String index, Map<String, String> criterias) {
    StringJoiner searchCriteria = new StringJoiner(", ");
    criterias.forEach((key, value) -> searchCriteria.add("{\"query\": {\"wildcard\": {\"" + key + "\": {\"value\": \"*" + value + "*\"}}}}"));

    String query = "{\n" +
            "  \"query\": {\n" +
            "    \"filtered\": {\n" +
            "      \"query\": {\n" +
            "        \"match_all\": {}\n" +
            "      },\n" +
            "      \"filter\": {\n" +
            "        \"bool\": {\n" +
            "          \"should\": [\n" +
                          searchCriteria.toString() +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    Search search = new Search.Builder(query)
            .addIndex(index)
            .addType(index.toUpperCase())
            .build();

    try {
      return jestConfig.getClient().execute(search);
    } catch (IOException e) {
      log.error("[ElasticSearchService::searchElementsMatch] Cannot find element: " + e.getMessage());
    }
    return null;
  }

  public SearchResult andCriteriaSearchElements(String index, Map<String, String> criterias) {
    StringJoiner searchCriteria = new StringJoiner(", ");
    criterias.forEach((key, value) -> searchCriteria.add("{\n" +
            "          \"wildcard\": {\n" +
            "            \"" + key + "\": {\n" +
            "              \"value\": \"*" + value + "*\"\n" +
            "            }\n" +
            "          }\n" +
            "        }"));

    String query = "{\n" +
            "  \"query\": {\n" +
            "    \"bool\": {\n" +
            "      \"must\": [\n" +
                    searchCriteria.toString() +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";

    System.out.println(query);

    Search search = new Search.Builder(query)
            .addIndex(index)
            .addType(index.toUpperCase())
            .build();

    try {
      return jestConfig.getClient().execute(search);
    } catch (IOException e) {
      log.error("[ElasticSearchService::searchElementsMatch] Cannot find element: " + e.getMessage());
    }
    return null;
  }

  public SearchResult findAllElements(String index) {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());

    Search search = new Search.Builder(searchSourceBuilder.toString())
            .addIndex(index)
            .addType(index.toUpperCase())
            .build();

    try {
      return jestConfig.getClient().execute(search);
    } catch (IOException e) {
      log.error("[ElasticSearchService::searchElementsMatch] Cannot find element: " + e.getMessage());
    }
    return null;
  }
}
