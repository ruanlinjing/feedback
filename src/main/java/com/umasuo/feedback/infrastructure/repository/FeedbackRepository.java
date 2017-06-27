package com.umasuo.feedback.infrastructure.repository;

import com.umasuo.feedback.domain.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Davis on 17/6/15.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

}
