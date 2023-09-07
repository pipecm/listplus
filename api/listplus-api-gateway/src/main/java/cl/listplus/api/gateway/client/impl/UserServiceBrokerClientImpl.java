package cl.listplus.api.gateway.client.impl;

import cl.listplus.api.gateway.client.UserServiceBrokerClient;
import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.events.Event;
import cl.listplus.api.gateway.events.EventType;
import cl.listplus.api.gateway.events.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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
    public void createUser(UserRequest userRequest) {
        UserEvent userEvent = new UserEvent();
        userEvent.setId(UUID.randomUUID().toString());
        userEvent.setSentAt(LocalDateTime.now());
        userEvent.setType(EventType.CREATE);
        userEvent.setData(userRequest);

        kafkaTemplate.send(topic, userEvent);
    }
}
