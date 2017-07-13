package com.umasuo.feedback.application.dto;

import com.umasuo.feedback.infrastructure.enums.FeedbackType;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * Created by umasuo on 17/6/27.
 * 用来新增反馈.
 */
@Data
public class FeedbackDraft {

  private String deviceId;

  private String title;

  private String content;

  private String phone;

  private String email;

  private FeedbackType type;
}
