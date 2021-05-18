package com.example.chat1.handler;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.time.Duration;
import java.util.*;

public class MyWebSocketHandler implements WebSocketHandler {
  @Override
  public Mono<Void> handle(WebSocketSession session) {
    Mono<Void> one = session.send(Flux.interval(Duration.ofMillis(3000))
      .map(s -> session.textMessage("hi " + s)));

    Mono<Void> two = session.send(session.receive()
      .map(WebSocketMessage::getPayloadAsText)
      .map(session::textMessage));

    Mono<Void> three = session.send(consume().map(s -> session.textMessage(s.value())));

    return Mono.zip(one, two, three).then();
  }

  public Flux<ReceiverRecord<String, String>> consume() {
    Map<String, Object> consumerProps = new HashMap<>();
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "foo");
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

    ReceiverOptions<String, String> receiverOptions =
      ReceiverOptions.<String, String>create(consumerProps)
        .subscription(Collections.singleton("test"));

    return KafkaReceiver.create(receiverOptions).receive();
  }

}
