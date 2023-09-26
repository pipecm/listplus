package cl.listplus.api.gateway.client.impl;

import cl.listplus.api.gateway.client.UserServiceBrokerClient;
import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.events.Event;
import cl.listplus.api.gateway.events.EventType;
import cl.listplus.api.gateway.events.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceBrokerClientImpl implements UserServiceBrokerClient {

    private KafkaTemplate<String, Event<?>> kafkaTemplate;

    @Value("${kafka.topics.user:users}")
    private String topic;

    public UserServiceBrokerClientImpl(KafkaTemplate<String, Event<?>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Async
    public void createUser(UserRequest userRequest) {
        sendEvent(EventType.CREATE, userRequest);
    }

    @Override
    @Async
    public void updateUser(UserRequest userRequest) {
        sendEvent(EventType.UPDATE, userRequest);
    }

    @Override
    @Async
    public void deleteUser(UserRequest userRequest) {
        sendEvent(EventType.DELETE, userRequest);
    }

    private void sendEvent(EventType eventType, UserRequest userRequest) {
        UserEvent userEvent = new UserEvent();
        userEvent.setId(UUID.randomUUID().toString());
        userEvent.setSentAt(LocalDateTime.now());
        userEvent.setType(eventType);
        userEvent.setData(userRequest);

        kafkaTemplate.send(topic, userEvent);
    }
}
