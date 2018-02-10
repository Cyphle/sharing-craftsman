package fr.knowledge.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class JestConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  JestClient client = null;

  @Value("${jest.elasticsearch.host}")
  String host;

  @Value("${jest.elasticsearch.port}")
  String port;

  @Value("${jest.elasticsearch.index}")
  String indexName;

  @Value("${jest.elasticsearch.mappings}")
  String mapping;

  public String getIndexName() {
    return indexName;
  }

  public String getMapping() {
    return mapping;
  }

  public JestClient getClient() {
    if (this.client == null) {
      JestClientFactory factory = new JestClientFactory();
      factory.setHttpClientConfig(new HttpClientConfig
              .Builder(host + ":" + port)
              .multiThreaded(true)
              .defaultMaxTotalConnectionPerRoute(2)
              .maxTotalConnection(2)
              .build());
      this.client = factory.getObject();
    }
    return this.client;
  }
}
