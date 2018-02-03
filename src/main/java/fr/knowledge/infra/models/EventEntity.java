package fr.knowledge.infra.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode
@ToString
@Entity
@Table(name = "events")
public class EventEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @Column(name = "event_id")
  private String eventId;
  @Column(name = "event_version")
  private int eventVersion;
  @Column(name = "timestamp")
  private Date timestamp;
  @Column(name = "aggreggate_id")
  private String aggreggateId;
  @Column(name = "payload_type")
  private String payloadType;
  @Column(name = "payload")
  private String payload;

  public EventEntity() {
  }

  public EventEntity(String eventId, int eventVersion, Date timestamp, String aggreggateId, String payloadType, String payload) {
    this.eventId = eventId;
    this.eventVersion = eventVersion;
    this.timestamp = timestamp;
    this.aggreggateId = aggreggateId;
    this.payloadType = payloadType;
    this.payload = payload;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public int getEventVersion() {
    return eventVersion;
  }

  public void setEventVersion(int eventVersion) {
    this.eventVersion = eventVersion;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getAggreggateId() {
    return aggreggateId;
  }

  public void setAggreggateId(String aggreggateId) {
    this.aggreggateId = aggreggateId;
  }

  public String getPayloadType() {
    return payloadType;
  }

  public void setPayloadType(String payloadType) {
    this.payloadType = payloadType;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }
}
