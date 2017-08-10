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
 * Created by umasuo on 17/6/27.
 */
@Service
public class FeedbackApplication {

  private static final Logger logger = LoggerFactory.getLogger(FeedbackApplication.class);

  @Autowired
  private transient FeedbackService service;

  @Transactional
  public FeedbackView addContent(String id, String contentStr, String developerId, String userId) {
    logger.debug("Enter. id: {}, contentStr: {}.", id, contentStr);

    Feedback feedback = service.get(id);

    if (StringUtils.isBlank(userId)) {
      addDeveloperContent(contentStr, developerId, feedback);
    } else {
      addUserContent(contentStr, userId, feedback);
    }

    service.save(feedback);

    logger.debug("Exit. saved: {}.", feedback);
    return FeedbackMapper.toView(feedback);
  }

  private void addDeveloperContent(String contentStr, String developerId, Feedback feedback) {
    logger.debug("Enter. contentStr: {}, developerId: {}, feedback: {}.",
        contentStr, developerId, feedback.getId());

    if (!developerId.equals(feedback.getDeveloperId())) {
      logger.debug("Can not add content to feedback: {} not belong to developer: {}.",
          feedback.getId(), developerId);
      throw new AuthFailedException("Feedback not belong to developer");
    }

    insertContent(feedback,  new Content(contentStr, developerId));

    feedback.setDeveloperStatus(FeedbackStatus.VIEWED);
    feedback.setUserStatus(FeedbackStatus.UNVIEWED);

    logger.debug("Exit.");
  }

  private void addUserContent(String contentStr, String userId, Feedback feedback) {
    logger.debug("Enter. contentStr: {}, userId: {}, feedback: {}.",
        contentStr, userId, feedback);

    if (!userId.equals(feedback.getUserId())) {
      logger.debug("Can not add content to feedback: {} not belong to user: {}.",
          feedback.getId(), userId);
      throw new AuthFailedException("Feedback not belong to user");
    }

    insertContent(feedback,  new Content(contentStr, userId));

    feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);
    feedback.setUserStatus(FeedbackStatus.VIEWED);

    logger.debug("Exit.");
  }

  private void insertContent(Feedback feedback, Content content) {
    logger.debug("Enter. feedback: {}, content: {}.", feedback.getId(), content);

    if (feedback.getContents() != null) {
      feedback.getContents().add(content);
    } else {
      feedback.setContents(Lists.newArrayList(content));
    }

    logger.debug("Exit.");
  }
}
