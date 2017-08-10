package com.umasuo.feedback.application.service;

import com.google.common.collect.Lists;
import com.umasuo.exception.AuthFailedException;
import com.umasuo.exception.ParametersException;
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

    if (StringUtils.isNotBlank(userId)) {
      addUserContent(id, contentStr, userId, feedback);
    } else {
      addDeveloperContent(id, contentStr, developerId, feedback);
    }

    logger.debug("Exit. saved: {}.", feedback);
    return FeedbackMapper.toView(feedback);
  }

  private void addDeveloperContent(String id, String contentStr, String developerId,
      Feedback feedback) {
    if (!developerId.equals(feedback.getDeveloperId())) {
      logger.debug("Can not add content to feedback: {} not belong to developer: {}.",
          id, developerId);
      throw new AuthFailedException("Feedback not belong to developer");
    }
    Content content = new Content(contentStr, developerId);
    insertContent(feedback, content);
    feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);
    feedback.setUserStatus(FeedbackStatus.VIEWED);
    service.save(feedback);
  }

  private void addUserContent(String id, String contentStr, String userId, Feedback feedback) {
    if (!userId.equals(feedback.getUserId())) {
      logger.debug("Can not add content to feedback: {} not belong to user: {}.", id, userId);
      throw new AuthFailedException("Feedback not belong to user");
    }
    Content content = new Content(contentStr, userId);
    insertContent(feedback, content);
    feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);
    feedback.setUserStatus(FeedbackStatus.VIEWED);
    service.save(feedback);
  }

  private void insertContent(Feedback feedback, Content content) {
    if (feedback.getContents() != null) {
      feedback.getContents().add(content);
    } else {
      feedback.setContents(Lists.newArrayList(content));
    }
  }
}
