package fr.knowledge.domain.library.valueobjects;

import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
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

  public String getCreatorContent() {
    return creator.getUsername();
  }

  public String getTitleContent() {
    return title.getTitle();
  }

  public String getContentContent() {
    return content.getContent();
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
}
