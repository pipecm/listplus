package cl.listplus.api.user.service;

import cl.listplus.api.user.dto.UserRequest;
import cl.listplus.api.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {
    User retrieveUser(Specification<User> specification);
    void createUser(UserRequest userRequest);
    void updateUser(UserRequest userRequest);
    void deleteUser(UUID id);
}

