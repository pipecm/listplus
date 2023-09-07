package cl.listplus.api.gateway.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User implements Serializable {
    private UUID id;
    private String username;
    private String email;
    private UserRole role;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private boolean active;
}
