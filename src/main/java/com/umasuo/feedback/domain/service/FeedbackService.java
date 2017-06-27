package com.umasuo.feedback.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.feedback.domain.model.Feedback;
import com.umasuo.feedback.infrastructure.enums.FeedbackStatus;
import com.umasuo.feedback.infrastructure.enums.FeedbackType;
import com.umasuo.feedback.infrastructure.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
@Service
public class FeedbackService {

  private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

  @Autowired
  private transient FeedbackRepository feedbackRepository;

  public Feedback save(Feedback feedback) {
    logger.debug("Enter. feedback: {}.", feedback);

    Feedback saved = feedbackRepository.save(feedback);

    logger.debug("Exit. savedFeedback: {}.", saved);
    return saved;
  }

  public Feedback get(String id) {
    logger.debug("Enter. id: {}.", id);

    Feedback feedback = feedbackRepository.findOne(id);
    if (feedback == null) {
      throw new NotExistException("Feedback not exit: " + id);
    }

    logger.debug("Exit. feedback: {}.", feedback);
    return feedback;
  }

  /**
   * 根据开发者的ID，反馈的处理状态和类型进行查询。
   * TODO 后期做分页.
   *
   * @param developerId 开发者ID
   * @param status      反馈状态
   * @param type        反馈种类
   * @return 反馈列表
   */
  public List<Feedback> getByDeveloper(String developerId, String userId, FeedbackStatus status,
                                       FeedbackType
                                           type) {
    logger.debug("Enter. developerId: {}, userId: {}, status: {}, type: {}.", developerId,
        userId, status, type);

    Feedback feedback = new Feedback();
    feedback.setDeveloperId(developerId);
    feedback.setDeveloperStatus(status);
    feedback.setType(type);
    Example<Feedback> example = Example.of(feedback);
    List<Feedback> feedbackList = feedbackRepository.findAll(example);

    logger.debug("Exit. feedbackSize: {}.", feedbackList.size());
    return feedbackList;
  }

  /**
   * 查询所有用户的反馈.
   * TODO 后期做分页.
   *
   * @param userId 用户userId
   * @return 反馈列表
   */
  public List<Feedback> getByUser(String userId) {
    logger.debug("Enter. userId: {}.", userId);

    Feedback feedback = new Feedback();
    feedback.setUserId(userId);
    Example<Feedback> example = Example.of(feedback);
    List<Feedback> feedbackList = feedbackRepository.findAll(example);

    logger.debug("Exit. feedbackSize: {}.", feedbackList.size());
    return feedbackList;
  }
}
