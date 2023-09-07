package cl.listplus.api.gateway.service.impl;

import cl.listplus.api.gateway.client.UserServiceBrokerClient;
import cl.listplus.api.gateway.client.UserServiceRestClient;
import cl.listplus.api.gateway.domain.User;
import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.domain.UserResponse;
import cl.listplus.api.gateway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private UserServiceBrokerClient userServiceBrokerClient;
    private UserServiceRestClient userServiceRestClient;

    public UserServiceImpl(UserServiceBrokerClient userServiceBrokerClient, UserServiceRestClient userServiceRestClient) {
        this.userServiceBrokerClient = userServiceBrokerClient;
        this.userServiceRestClient = userServiceRestClient;
    }

    @Override
    public Mono<User> retrieveUser(UserRequest userRequest) {
        return userServiceRestClient.retrieveUser(userRequest);
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return userServiceRestClient.userAlreadyExists(userRequest)
                .map(alreadyExists -> sendToBroker(alreadyExists, userRequest));
    }

    @Override
    public Mono<Void> updateUser(UserRequest userRequest) {
        return null;
    }

    @Override
    public Mono<Void> deleteUser(UserRequest userRequest) {
        return null;
    }

    private UserResponse sendToBroker(boolean alreadyExists, UserRequest userRequest) {
        if (alreadyExists) {
            return UserResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("User already exists")
                    .build();
        } else {
            try {
                userServiceBrokerClient.createUser(userRequest);
                return UserResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("User created successfully")
                        .build();
            } catch (Exception exception) {
                return UserResponse.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build();
            }
        }
    }
}
