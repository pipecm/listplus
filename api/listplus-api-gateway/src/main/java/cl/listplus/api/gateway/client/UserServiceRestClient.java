package cl.listplus.api.gateway.client;

import cl.listplus.api.gateway.domain.User;
import cl.listplus.api.gateway.domain.UserRequest;
import reactor.core.publisher.Mono;

public interface UserServiceRestClient {
    Mono<User> retrieveUser(UserRequest userRequest);
    Mono<Boolean> userAlreadyExists(UserRequest userRequest);
}
