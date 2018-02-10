package fr.knowledge.infra.events.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.knowledge.domain.comments.events.CommentAddedEvent;
import fr.knowledge.domain.common.DomainEvent;
import fr.knowledge.domain.common.valueobjects.Content;
import fr.knowledge.domain.common.valueobjects.ContentType;
import fr.knowledge.domain.common.valueobjects.Id;
import fr.knowledge.domain.common.valueobjects.Username;

public class CommentAddedInfraEvent {
  @JsonProperty(value = "id")
  private String id;
  @JsonProperty(value = "commenter")
  private String commenter;
  @JsonProperty(value = "contentType")
  private String contentType;
  @JsonProperty(value = "contentId")
  private String contentId;
  @JsonProperty(value = "content")
  private String content;

  public CommentAddedInfraEvent() {
  }

  public CommentAddedInfraEvent(String id, String commenter, String contentType, String contentId, String content) {
    this.id = id;
    this.commenter = commenter;
    this.contentType = contentType;
    this.contentId = contentId;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCommenter() {
    return commenter;
  }

  public void setCommenter(String commenter) {
    this.commenter = commenter;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public CommentAddedEvent fromInfraToDomain() {
    return new CommentAddedEvent(Id.of(id), Username.from(commenter), ContentType.valueOf(contentType), Id.of(contentId), Content.of(content));
  }

  public static CommentAddedInfraEvent fromDomainToInfra(DomainEvent event) {
    return new CommentAddedInfraEvent(event.getAggregateId(), ((CommentAddedEvent) event).getCommenterContent(), ((CommentAddedEvent) event).getContentType().name(), ((CommentAddedEvent) event).getContentIdContent(), ((CommentAddedEvent) event).getContentContent());
  }
}
