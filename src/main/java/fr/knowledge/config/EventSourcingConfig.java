package fr.knowledge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventSourcingConfig {
  @Value("${cqrs.version}")
  private String version;

  public String getVersion() {
    return version;
  }
}
