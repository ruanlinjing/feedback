package com.umasuo.feedback.application.dto;

import lombok.Data;

/**
 * Created by umasuo on 17/6/27.
 */
@Data
public class ContentView {

  /**
   * create time for this content.
   */
  protected Long createdAt;

  /**
   * the real content.
   */
  private String contents;

}
