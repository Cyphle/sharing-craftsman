package fr.knowledge.domain.library.aggregates;

public class UpdateCategoryException extends Exception {
  public UpdateCategoryException(String message) {
    super(message);
  }
}
