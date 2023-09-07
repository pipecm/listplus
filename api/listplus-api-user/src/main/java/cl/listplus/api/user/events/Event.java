package cl.listplus.api.user.events;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Event<T> {
    private String id;
    private LocalDateTime sentAt;
    private LocalDateTime receivedAt;
    private EventType type;
    private T data;
}
