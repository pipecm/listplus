package cl.listplus.api.gateway.service;

import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.domain.UserResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<UserResponse> retrieveUser(UUID id);
    Mono<UserResponse> retrieveUser(UserRequest userRequest);
    Mono<UserResponse> createUser(UserRequest userRequest);
    Mono<UserResponse> updateUser(UserRequest userRequest);
    Mono<UserResponse> deleteUser(UserRequest userRequest);
}
