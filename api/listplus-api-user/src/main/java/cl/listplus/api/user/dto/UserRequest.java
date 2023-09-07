package cl.listplus.api.user.dto;

import cl.listplus.api.user.model.Role;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserRequest implements Serializable {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private boolean active;
}