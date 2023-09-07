package cl.listplus.api.gateway.service;

import cl.listplus.api.gateway.domain.User;
import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.domain.UserResponse;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> retrieveUser(UserRequest userRequest);
    Mono<UserResponse> createUser(UserRequest userRequest);
    Mono<Void> updateUser(UserRequest userRequest);
    Mono<Void> deleteUser(UserRequest userRequest);
}
