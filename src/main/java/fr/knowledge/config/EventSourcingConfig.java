package fr.knowledge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventSourcingConfig {
  @Value("${cqrs.version}")
  private int version;

  public int getVersion() {
    return version;
  }
}
