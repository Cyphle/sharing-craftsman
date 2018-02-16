package fr.knowledge;

import fr.knowledge.config.CommandBusConfig;
import fr.knowledge.config.EventBusConfig;
import fr.knowledge.config.QueryBusConfig;
import fr.knowledge.infra.repositories.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CommandBusConfig commandBusConfig;
  @Autowired
  private EventBusConfig eventBusConfig;
  @Autowired
  private QueryBusConfig queryBusConfig;
  @Autowired
  private ElasticSearchService elasticSearchService;

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    log.info("[ApplicationInitializer::onApplicationEvent] -- Initialize Elastic search indexes and CQRS buses");
    eventBusConfig.configure();
    commandBusConfig.configure();
    elasticSearchService.createIndexes();
    queryBusConfig.configure();
  }
}
