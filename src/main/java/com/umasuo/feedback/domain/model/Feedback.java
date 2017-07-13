package com.umasuo.feedback.domain.model;

import com.umasuo.feedback.infrastructure.enums.FeedbackStatus;
import com.umasuo.feedback.infrastructure.enums.FeedbackType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Created by umasuo on 17/6/27.
 */
@Data
@Entity
@Table(name = "feedback")
@EntityListeners(AuditingEntityListener.class)
public class Feedback {

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
  private Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  private Long lastModifiedAt;

  @Version
  private long version;

  /**
   * 用户ID.
   */
  private String userId;

  /**
   * 开发者ID.
   */
  private String developerId;

  /**
   * 设备ID.
   */
  private String deviceId;

  /**
   * 反馈状态.
   */
  private FeedbackStatus developerStatus;

  /**
   * 用户的状态.
   */
  private FeedbackStatus userStatus;

  /**
   * 反馈种类：感谢类，疑问类，错误类，投诉类等
   */
  private FeedbackType type;

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  @OrderBy("created_at ASC")
  private List<Content> contents;

  private String title;

  private String phone;

  private String email;

}
