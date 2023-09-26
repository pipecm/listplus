package cl.listplus.api.gateway.client;

import cl.listplus.api.gateway.domain.UserRequest;

public interface UserServiceBrokerClient {
    void createUser(UserRequest userRequest);
    void updateUser(UserRequest userRequest);
    void deleteUser(UserRequest userRequest);
}
