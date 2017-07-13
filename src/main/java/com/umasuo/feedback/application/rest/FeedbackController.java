package com.umasuo.feedback.application.rest;

import com.umasuo.feedback.application.dto.ContentDraft;
import com.umasuo.feedback.application.dto.FeedbackDraft;
import com.umasuo.feedback.application.dto.FeedbackView;
import com.umasuo.feedback.application.dto.mapper.FeedbackMapper;
import com.umasuo.feedback.application.service.FeedbackApplication;
import com.umasuo.feedback.domain.model.Feedback;
import com.umasuo.feedback.domain.service.FeedbackService;
import com.umasuo.feedback.infrastructure.Router;
import com.umasuo.feedback.infrastructure.enums.FeedbackStatus;
import com.umasuo.feedback.infrastructure.enums.FeedbackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
@RestController
@CrossOrigin
public class FeedbackController {

  private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

  @Autowired
  private transient FeedbackService feedbackService;

  @Autowired
  private transient FeedbackApplication feedbackApplication;

  /**
   * 获取开发者所有的反馈.
   * 该接口由开发者后台调用.
   *
   * @param developerId
   * @param userId
   * @param deviceId
   * @param status
   * @param type
   * @return
   */
  @GetMapping(value = Router.FEEDBACK_ROOT, headers = "developerId")
  public List<FeedbackView> getForDeveloper(@RequestHeader String developerId,
                                            @RequestParam(required = false) String userId,
                                            @RequestParam(required = false) String deviceId,
                                            @RequestParam(required = false) FeedbackStatus status,
                                            @RequestParam(required = false) FeedbackType type) {
    logger.info("Enter. developerId: {}, userId: {}, deviceId: {}, status: {}, type: {}.",
        developerId, userId, deviceId, status, type);

    List<Feedback> feedbackList = feedbackService.getByDeveloper(developerId, userId, status, type);
    List<FeedbackView> feedbackViews = FeedbackMapper.toView(feedbackList);

    logger.info("Exit. feedbackSize: {}.", feedbackViews.size());
    return feedbackViews;
  }

  @GetMapping(value = Router.FEEDBACK_ROOT, headers = "userId")
  public List<FeedbackView> getForUser(@RequestHeader String userId) {
    logger.info("Enter. userId: {}.");

    List<Feedback> feedbackList = feedbackService.getByUser(userId);
    List<FeedbackView> feedbackViews = FeedbackMapper.toView(feedbackList);

    logger.info("Exit. feedbackSize: {}.", feedbackViews.size());
    return feedbackViews;
  }


  @PostMapping(Router.FEEDBACK_ROOT)
  public void addFeedback(FeedbackDraft draft) {
    logger.info("Enter. draft: {}.", draft);

    feedbackService.save(FeedbackMapper.toEntity(draft));

    logger.info("Exit.");
  }

  @PostMapping(Router.FEEDBACK_WITH_ID)
  public FeedbackView addContent(@PathVariable String id,
                                 @RequestHeader String developerId,
                                 @RequestHeader String userId,
                                 @RequestBody ContentDraft draft) {
    logger.info("Enter. id: {}, draft: {}.", id, draft);

    FeedbackView view = feedbackApplication.addContent(id, draft.getContent(), developerId, userId);

    logger.info("Exit. view: {}.", view);
    return view;
  }
}
