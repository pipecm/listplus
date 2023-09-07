package cl.listplus.api.user.events;

import cl.listplus.api.user.dto.UserRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEvent extends Event<UserRequest> {

}
