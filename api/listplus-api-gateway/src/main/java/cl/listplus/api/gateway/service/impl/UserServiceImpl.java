package cl.listplus.api.gateway.service.impl;

import cl.listplus.api.gateway.client.UserServiceBrokerClient;
import cl.listplus.api.gateway.client.UserServiceRestClient;
import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.domain.UserResponse;
import cl.listplus.api.gateway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserServiceBrokerClient userServiceBrokerClient;
    private UserServiceRestClient userServiceRestClient;

    public UserServiceImpl(UserServiceBrokerClient userServiceBrokerClient, UserServiceRestClient userServiceRestClient) {
        this.userServiceBrokerClient = userServiceBrokerClient;
        this.userServiceRestClient = userServiceRestClient;
    }

    @Override
    public Mono<UserResponse> retrieveUser(UUID id) {
        return userServiceRestClient.retrieveUser(id)
                .map(UserResponse::new)
                .onErrorResume(error -> Mono.just(new UserResponse(HttpStatus.NOT_FOUND, "User not found")));
    }

    @Override
    public Mono<UserResponse> retrieveUser(UserRequest userRequest) {
        return userServiceRestClient.retrieveUser(userRequest)
                .map(UserResponse::new)
                .onErrorResume(error -> Mono.just(new UserResponse(HttpStatus.NOT_FOUND, "User not found")));
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return userServiceRestClient.retrieveUser(userRequest)
                .map(user -> true)
                .onErrorResume(error -> Mono.just(false))
                .map(alreadyExists -> {
                    if (alreadyExists) {
                        return new UserResponse(HttpStatus.CONFLICT, "User already exists");
                    } else {
                        try {
                            userServiceBrokerClient.createUser(userRequest);
                            return new UserResponse(HttpStatus.CREATED, "User created successfully");
                        } catch (Exception exception) {
                            return new UserResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
                        }
                    }
                });
    }

    @Override
    public Mono<UserResponse> updateUser(UserRequest userRequest) {
        return userServiceRestClient.retrieveUser(userRequest.getId())
                .map(user -> true)
                .onErrorResume(error -> Mono.just(false))
                .map(userExists -> {
                    if (userExists) {
                        try {
                            userServiceBrokerClient.updateUser(userRequest);
                            return new UserResponse(HttpStatus.OK, "User updated successfully");
                        } catch (Exception exception) {
                            return new UserResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
                        }
                    } else {
                        return new UserResponse(HttpStatus.NOT_FOUND, "User does not exist");
                    }
                });
    }

    @Override
    public Mono<UserResponse> deleteUser(UserRequest userRequest) {
        return userServiceRestClient.retrieveUser(userRequest.getId())
                .map(user -> true)
                .onErrorResume(error -> Mono.just(false))
                .map(userExists -> {
                    if (userExists) {
                        try {
                            userServiceBrokerClient.deleteUser(userRequest);
                            return new UserResponse(HttpStatus.NO_CONTENT, "User deleted successfully");
                        } catch (Exception exception) {
                            return new UserResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
                        }
                    } else {
                        return new UserResponse(HttpStatus.NOT_FOUND, "User does not exist");
                    }
                });
    }
}
