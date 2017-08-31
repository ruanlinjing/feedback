package com.umasuo.feedback.application.service;

import com.google.common.collect.Lists;
import com.umasuo.exception.AuthFailedException;
import com.umasuo.feedback.application.dto.FeedbackView;
import com.umasuo.feedback.application.dto.mapper.FeedbackMapper;
import com.umasuo.feedback.domain.model.Content;
import com.umasuo.feedback.domain.model.Feedback;
import com.umasuo.feedback.domain.service.FeedbackService;
import com.umasuo.feedback.infrastructure.enums.FeedbackStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Feedback application.
 */
@Service
public class FeedbackApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackApplication.class);

  /**
   * Feedback service.
   */
  @Autowired
  private transient FeedbackService service;

  /**
   * Add content.
   *
   * @param id
   * @param contentStr
   * @param developerId
   * @param userId
   * @return
   */
  @Transactional
  public FeedbackView addContent(String id, String contentStr, String developerId, String userId) {
    LOGGER.debug("Enter. id: {}, contentStr: {}.", id, contentStr);

    Feedback feedback = service.get(id);

    if (StringUtils.isBlank(userId)) {
      addDeveloperContent(contentStr, developerId, feedback);
    } else {
      addUserContent(contentStr, userId, feedback);
    }

    service.save(feedback);

    LOGGER.debug("Exit. saved: {}.", feedback);
    return FeedbackMapper.toView(feedback);
  }

  /**
   * Add developer content.
   *
   * @param contentStr
   * @param developerId
   * @param feedback
   */
  private void addDeveloperContent(String contentStr, String developerId, Feedback feedback) {
    LOGGER.debug("Enter. contentStr: {}, developerId: {}, feedback: {}.",
      contentStr, developerId, feedback.getId());

    if (!developerId.equals(feedback.getDeveloperId())) {
      LOGGER.debug("Can not add content to feedback: {} not belong to developer: {}.",
        feedback.getId(), developerId);
      throw new AuthFailedException("Feedback not belong to developer");
    }

    insertContent(feedback, new Content(contentStr, developerId));

    feedback.setDeveloperStatus(FeedbackStatus.VIEWED);
    feedback.setUserStatus(FeedbackStatus.UNVIEWED);

    LOGGER.debug("Exit.");
  }

  /**
   * Add user content.
   *
   * @param contentStr
   * @param userId
   * @param feedback
   */
  private void addUserContent(String contentStr, String userId, Feedback feedback) {
    LOGGER.debug("Enter. contentStr: {}, userId: {}, feedback: {}.",
      contentStr, userId, feedback);

    if (!userId.equals(feedback.getUserId())) {
      LOGGER.debug("Can not add content to feedback: {} not belong to user: {}.",
        feedback.getId(), userId);
      throw new AuthFailedException("Feedback not belong to user");
    }

    insertContent(feedback, new Content(contentStr, userId));

    feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);
    feedback.setUserStatus(FeedbackStatus.VIEWED);

    LOGGER.debug("Exit.");
  }

  /**
   * Insert content.
   *
   * @param feedback
   * @param content
   */
  private void insertContent(Feedback feedback, Content content) {
    LOGGER.debug("Enter. feedback: {}, content: {}.", feedback.getId(), content);

    if (feedback.getContents() == null) {
      feedback.setContents(Lists.newArrayList(content));
    } else {
      feedback.getContents().add(content);
    }

    LOGGER.debug("Exit.");
  }
}
