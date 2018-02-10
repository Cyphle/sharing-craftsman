package fr.knowledge;

import fr.knowledge.config.CommandBusConfig;
import fr.knowledge.config.EventBusConfig;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {
  @Autowired
  private CommandBusConfig commandBusConfig;
  @Autowired
  private EventBusConfig eventBusConfig;
  @Autowired
  private ElasticSearchService elasticSearchService;

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    eventBusConfig.configure();
    commandBusConfig.configure();
    elasticSearchService.createIndexes();
  }
}
