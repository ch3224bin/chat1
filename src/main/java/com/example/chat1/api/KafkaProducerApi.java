package com.example.chat1.api;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerApi {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaProducerApi(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @RequestMapping("/send")
  public void sendMessage(@RequestParam("message") String message) {
    kafkaTemplate.send("test", message);
  }
}
