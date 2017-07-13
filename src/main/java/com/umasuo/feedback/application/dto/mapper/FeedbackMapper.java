package com.umasuo.feedback.application.dto.mapper;

import com.umasuo.feedback.application.dto.FeedbackDraft;
import com.umasuo.feedback.application.dto.FeedbackView;
import com.umasuo.feedback.domain.model.Content;
import com.umasuo.feedback.domain.model.Feedback;
import com.umasuo.feedback.infrastructure.enums.FeedbackStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
@Data
public class FeedbackMapper {

  public static FeedbackView toView(Feedback feedback) {

    FeedbackView view = new FeedbackView();
    view.setId(feedback.getId());
    view.setCreatedAt(feedback.getCreatedAt());
    view.setLastModifiedAt(feedback.getLastModifiedAt());
    view.setVersion(feedback.getVersion());
    view.setUserId(feedback.getUserId());
    view.setDeveloperId(feedback.getDeveloperId());
    view.setDeviceId(feedback.getDeviceId());
    view.setDeveloperStatus(feedback.getDeveloperStatus());
    view.setUserStatus(feedback.getUserStatus());
    view.setTitle(feedback.getTitle());
    view.setEmail(feedback.getEmail());
    view.setPhone(feedback.getPhone());

    view.setContents(ContentMapper.toView(feedback.getContents()));

    return view;
  }

  public static List<FeedbackView> toView(List<Feedback> feedbackList) {
    List<FeedbackView> list = new ArrayList<>();
    feedbackList.stream().forEach(
        feedback -> list.add(toView(feedback))
    );
    return list;
  }

  public static Feedback toEntity(String userId, String developerId, FeedbackDraft draft) {
    Feedback feedback = new Feedback();
    feedback.setUserId(userId);
    feedback.setDeveloperId(developerId);
    feedback.setDeviceId(draft.getDeviceId());
    feedback.setTitle(draft.getTitle());
    feedback.setPhone(draft.getPhone());
    feedback.setEmail(draft.getEmail());
    feedback.setType(draft.getType());

    Content content = new Content();
    content.setContent(draft.getContent());
    List<Content> contents = new ArrayList<>();
    contents.add(content);
    feedback.setContents(contents);

    feedback.setUserStatus(FeedbackStatus.VIEWED);
    feedback.setDeveloperStatus(FeedbackStatus.UNVIEWED);
    return feedback;
  }

}
