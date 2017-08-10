package com.umasuo.feedback.application.dto.mapper;

import com.umasuo.feedback.application.dto.ContentView;
import com.umasuo.feedback.domain.model.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
public final class ContentMapper {

  /**
   * Instantiates a new Content mapper.
   */
  private ContentMapper() {
  }

  /**
   * Convert Content to ContentView.
   *
   * @param content the Content
   * @return ContentView content view
   */
  public static ContentView toView(Content content) {
    ContentView view = new ContentView();
    view.setContents(content.getContent());
    view.setCreatedAt(content.getCreatedAt());
    view.setOwnerId(content.getOwnerId());
    return view;
  }

  /**
   * Convent List of Content to List of ContentView.
   *
   * @param contentList List of Content
   * @return List of ContentView
   */
  public static List<ContentView> toView(List<Content> contentList) {
    List<ContentView> contentViews = new ArrayList<>();

    if (contentList != null) {
      contentList.stream().forEach(
          content -> contentViews.add(toView(content))
      );
    }
    return contentViews;
  }
}
