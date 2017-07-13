package com.umasuo.feedback.application.service;

import com.umasuo.exception.ParametersException;
import com.umasuo.feedback.application.dto.FeedbackView;
import com.umasuo.feedback.application.dto.mapper.FeedbackMapper;
import com.umasuo.feedback.domain.model.Content;
import com.umasuo.feedback.domain.model.Feedback;
import com.umasuo.feedback.domain.service.FeedbackService;
import com.umasuo.feedback.infrastructure.enums.FeedbackStatus;
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

    if (developerId == null && userId == null) {
      throw new ParametersException("Both developer and userId is null.");
    }
    
    Feedback feedback = service.get(id);
    Content content = new Content();
    content.setContent(contentStr);
    feedback.getContents().add(content);

    if (developerId == null && userId != null) {
      feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);
      feedback.setUserStatus(FeedbackStatus.VIEWED);
    } else if (developerId != null && userId == null) {
      feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);
      feedback.setUserStatus(FeedbackStatus.VIEWED);
    }

    Feedback saved = service.save(feedback);

    logger.debug("Exit. saved: {}.", saved);
    return FeedbackMapper.toView(saved);
  }
}
