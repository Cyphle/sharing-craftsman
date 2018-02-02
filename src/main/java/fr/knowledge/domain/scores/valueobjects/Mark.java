package fr.knowledge.domain.scores.valueobjects;

import java.util.Arrays;

public enum Mark {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

  public final int value;

  Mark(int value) {
    this.value = value;
  }

  public static Mark findByValue(int value) {
    return Arrays.stream(Mark.values())
            .filter(mark -> mark.value == value)
            .findAny()
            .orElse(Mark.ONE);
  }
}
