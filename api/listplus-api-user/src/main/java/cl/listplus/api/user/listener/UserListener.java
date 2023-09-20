package cl.listplus.api.user.listener;

import cl.listplus.api.user.events.Event;
import cl.listplus.api.user.events.UserEvent;
import cl.listplus.api.user.exception.UserServiceException;
import cl.listplus.api.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static cl.listplus.api.user.utils.UserServiceMessages.INVALID_EVENT_TYPE_ERROR_MSG;
import static cl.listplus.api.user.utils.UserServiceMessages.RECEIVED_EVENT_LOG_MSG;

@Slf4j
@Component
public class UserListener {

    private UserService userService;

    public UserListener(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "${kafka.topics.user:users}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${kafka.groups.user:users}")
    public void receive(@Payload Event<?> event) {
        if (event.getClass().isAssignableFrom(UserEvent.class)) {
            UserEvent userEvent = (UserEvent) event;
            userEvent.setReceivedAt(LocalDateTime.now());

            log.info(RECEIVED_EVENT_LOG_MSG, userEvent.getId(), userEvent.getReceivedAt(), userEvent.getData().toString());

            try {
                switch (userEvent.getType()) {
                    case CREATE:
                        userService.createUser(userEvent.getData());
                        break;
                    case UPDATE:
                        userService.updateUser(userEvent.getData());
                        break;
                    case DELETE:
                        userService.deleteUser(userEvent.getData().getId());
                        break;
                    default:
                        throw new UserServiceException(HttpStatus.BAD_REQUEST, INVALID_EVENT_TYPE_ERROR_MSG);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}

