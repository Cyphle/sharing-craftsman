package fr.knowledge.infra.repositories;

import fr.knowledge.KnowledgeLibraryApplication;
import fr.knowledge.common.DateConverter;
import fr.knowledge.domain.library.commands.CreateCategoryCommand;
import fr.knowledge.infra.models.EventEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KnowledgeLibraryApplication.class})
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class EventStoreTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private EventStore eventStore;

  @Test
  public void should_save_something() {
    EventEntity entity = new EventEntity("aaa", 1, DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 37, 12)), "aaa", CreateCategoryCommand.class.getName(), "{id: \"aaa\"}");
    eventStore.save(entity);

    assertThat(eventStore.findAll()).containsExactly(entity);
  }
}
