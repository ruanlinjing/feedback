package com.umasuo.feedback.application.dto.mapper;

import com.umasuo.feedback.application.dto.ContentView;
import com.umasuo.feedback.domain.model.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
public class ContentMapper {

  public static ContentView toView(Content content) {
    ContentView view = new ContentView();
    view.setContents(content.getContent());
    view.setCreatedAt(content.getCreatedAt());
    return view;
  }

  public static List<ContentView> toView(List<Content> contentList) {
    List<ContentView> contentViews = new ArrayList<>();
    contentList.stream().forEach(
        content -> contentViews.add(toView(content))
    );
    return contentViews;
  }

  public static Content toEntity(ContentView view) {
    Content content = new Content();
    content.setContent(view.getContents());
    return content;
  }
}
