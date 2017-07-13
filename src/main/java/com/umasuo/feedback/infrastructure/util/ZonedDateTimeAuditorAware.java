package com.umasuo.feedback.infrastructure.util;

import org.springframework.data.domain.AuditorAware;

/**
 * Zoned data time auditor.
 */
public class ZonedDateTimeAuditorAware implements AuditorAware<String> {

  /**
   * Return null auditor.
   *
   * @return null
   */
  @Override
  public String getCurrentAuditor() {
    return null;
  }
}