package com.umasuo.feedback.application.dto;

import com.umasuo.feedback.infrastructure.enums.FeedbackType;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Created by umasuo on 17/6/27.
 * 用来新增反馈.
 */
@Data
public class FeedbackDraft {

  /**
   * The device id, required.
   */
  @NotNull
  @Size(min = 2)
  private String deviceId;

  /**
   * The title.
   */
  private String title;

  /**
   * The content, required.
   */
  @NotNull
  @Size(min = 1)
  private String content;

  /**
   * The phone.
   */
  private String phone;

  /**
   * The email.
   */
  private String email;

  /**
   * FeedBack type, required.
   */
  @NotNull
  private FeedbackType type;
}
