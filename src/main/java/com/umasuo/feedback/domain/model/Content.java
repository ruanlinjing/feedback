package com.umasuo.feedback.domain.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Feedback content.
 */
@Data
@Entity
@Table(name = "feedback_content")
@EntityListeners(AuditingEntityListener.class)
public class Content {

  /**
   * Uuid.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  protected Long createdAt;

  /**
   * The content, required.
   */
  @Column(length = 2048, nullable = false)
  private String content;

  /**
   * The owner id, required.
   * Owner maybe user or developer.
   */
  @Column
  private String ownerId;

  /**
   * Instantiates a new Content.
   *
   * @param content the content
   * @param ownerId the owner id
   */
  public Content(String content, String ownerId) {
    this.content = content;
    this.ownerId = ownerId;
  }
}
