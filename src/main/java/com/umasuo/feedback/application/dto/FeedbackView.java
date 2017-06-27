package com.umasuo.feedback.application.dto;

import com.umasuo.feedback.infrastructure.enums.FeedbackStatus;
import com.umasuo.feedback.infrastructure.enums.FeedbackType;
import lombok.Data;

import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
@Data
public class FeedbackView {

  /**
   * Uuid.
   */
  private String id;

  /**
   * The Created at.
   */
  private Long createdAt;

  /**
   * The Last modified at.
   */
  private Long lastModifiedAt;

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

  private List<ContentView> contents;

  private String title;

  private String phone;

  private String email;
}
