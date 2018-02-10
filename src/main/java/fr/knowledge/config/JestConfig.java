package fr.knowledge.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JestConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  JestClient client = null;

  @Value("${spring.elasticsearch.jest.uris}")
  String host;

  @Value("${spring.elasticsearch.jest.index}")
  String[] indexNames;

  public Map<String, String> getIndexNames() {
    Map<String, String> indexes = new HashMap<>();
    Arrays.stream(indexNames)
            .map(index -> index.split(":"))
            .forEach(index -> indexes.put(index[0], index[1]));
    return indexes;
  }

  public JestClient getClient() {
    if (this.client == null) {
      JestClientFactory factory = new JestClientFactory();
      factory.setHttpClientConfig(new HttpClientConfig
              .Builder(host)
              .multiThreaded(true)
              .defaultMaxTotalConnectionPerRoute(2)
              .maxTotalConnection(2)
              .build());
      this.client = factory.getObject();
    }
    return this.client;
  }
}
