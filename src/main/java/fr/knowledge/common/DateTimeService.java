package fr.knowledge.common;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DateTimeService implements DateService {
  @Override
  public Date nowInDate() {
    return DateConverter.fromLocalDateTimeToDate(LocalDateTime.now());
  }

  @Override
  public LocalDateTime now() {
    return LocalDateTime.now();
  }

  @Override
  public LocalDateTime getDayAt(int offsetDays) {
    return LocalDateTime.now().plusDays(offsetDays);
  }
}
