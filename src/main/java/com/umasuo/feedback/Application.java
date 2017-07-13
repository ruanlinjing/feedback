package com.umasuo.feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/2/9.
 */
@SpringBootApplication(scanBasePackages = "com.umasuo.feedback")
@EnableAutoConfiguration
@Configuration
@RestController
@EnableScheduling
public class Application {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  /**
   * service name.
   */
  @Value("${spring.application.name:feedback-service}")
  private String serviceName;

  /**
   * This api is used for health check.
   *
   * @return service name.
   */
  @GetMapping("/")
  public String index() {
    return serviceName + ", system time: " + System.currentTimeMillis();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
