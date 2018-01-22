package fr.knowledge.domain.library.valueobjects;

import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

public class Knowledge {
  private final Id id;
  private Username creator;
  private Title title;
  private Content content;

  private Knowledge(Id id, Username creator, Title title, Content content) {
    this.id = id;
    this.creator = creator;
    this.title = title;
    this.content = content;
  }

  public Id getId() {
    return id;
  }

  public boolean hasEmptyCreator() {
    return creator.isEmpty();
  }

  public boolean hasEmptyTitle() {
    return title.isEmpty();
  }

  public boolean hasEmptyContent() {
    return content.isEmpty();
  }

  public void update(Knowledge knowledge) {
    creator = knowledge.creator;
    title = knowledge.title;
    content = knowledge.content;
  }

  public static Knowledge of(String id, String creator, String title, String content) {
    return new Knowledge(Id.of(id), Username.from(creator), Title.of(title), Content.of(content));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Knowledge knowledge = (Knowledge) o;

    if (id != null ? !id.equals(knowledge.id) : knowledge.id != null) return false;
    if (creator != null ? !creator.equals(knowledge.creator) : knowledge.creator != null) return false;
    if (title != null ? !title.equals(knowledge.title) : knowledge.title != null) return false;
    return content != null ? content.equals(knowledge.content) : knowledge.content == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (creator != null ? creator.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Knowledge{" +
            "id=" + id +
            ", creator=" + creator +
            ", title=" + title +
            ", content=" + content +
            '}';
  }
}
