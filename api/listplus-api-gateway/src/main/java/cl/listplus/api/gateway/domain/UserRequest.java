package cl.listplus.api.gateway.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Data
@Builder
@FieldNameConstants()
public class UserRequest {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private boolean active;
}
