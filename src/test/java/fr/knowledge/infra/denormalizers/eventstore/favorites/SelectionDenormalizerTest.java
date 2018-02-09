package fr.knowledge.infra.denormalizers.eventstore.favorites;

import fr.knowledge.common.DateConverter;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.favorites.aggregates.Selection;
import fr.knowledge.domain.favorites.events.SelectionRemovedEvent;
import fr.knowledge.infra.models.EventEntity;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectionDenormalizerTest {
  @Test
  public void should_rebuild_selection_state() {
    EventEntity selectionAddedEvent = new EventEntity(
            "aaa",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12)),
            "saa",
            "fr.knowledge.infra.events.favorites.SelectionAddedInfraEvent",
            "{\"id\":\"saa\",\"username\":\"john@doe.fr\",\"contentType\":\"CATEGORY\",\"contentId\":\"aaa\"}"
    );
    EventEntity selectionRemovedEvent = new EventEntity(
            "aab",
            1,
            DateConverter.fromLocalDateTimeToDate(LocalDateTime.of(2018, Month.FEBRUARY, 3, 16, 50, 12)),
            "saa",
            "fr.knowledge.infra.events.favorites.SelectionRemovedInfraEvent",
            "{\"id\":\"saa\"}"
    );

    Optional<Selection> denormalizedSelection = SelectionDenormalizer.denormalize(Arrays.asList(selectionAddedEvent, selectionRemovedEvent));

    Selection selection = Selection.of("saa", "john@doe.fr", ContentType.CATEGORY, "aaa");
    selection.apply(new SelectionRemovedEvent(Id.of("saa")));
    assertThat(denormalizedSelection.get()).isEqualTo(selection);
  }
}
