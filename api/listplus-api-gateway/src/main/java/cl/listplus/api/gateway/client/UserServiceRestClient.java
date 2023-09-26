package cl.listplus.api.gateway.client;

import cl.listplus.api.gateway.domain.User;
import cl.listplus.api.gateway.domain.UserRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserServiceRestClient {
    Mono<User> retrieveUser(UUID id);
    Mono<User> retrieveUser(UserRequest userRequest);
}
