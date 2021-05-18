## kafka 토픽 만들고 메세지 send

참고 사이트 :
- https://galid1.tistory.com/792
- https://victorydntmd.tistory.com/348
- https://wedul.site/574
- https://projectreactor.io/docs/kafka/release/reference/

```bash
docker exec -it chat1_kafka_1

kafka-topics --create --topic test --bootstrap-server localhost:9092

kafka-console-producer --topic test --bootstrap-server localhost:9092
```

## 하려는 일

- 기본 명령어는 /command를 통해 즉시 응답 받는다.
- 사용자 push 알림은 kafka를 통해 받음

## TODO

- [ ] kafka 메세지 객체로
- [ ] kafka 메세지를 사용자 별로 나눠 뿌림
