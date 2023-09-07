package cl.listplus.api.gateway.events;

import cl.listplus.api.gateway.domain.UserRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEvent extends Event<UserRequest> {

}
