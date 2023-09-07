package cl.listplus.api.gateway.events;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Event<T> {
    private String id;
    private LocalDateTime sentAt;
    private EventType type;
    private T data;
}
