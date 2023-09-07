package cl.listplus.api.user.service.impl;

import cl.listplus.api.user.dto.UserRequest;
import cl.listplus.api.user.exception.UserServiceException;
import cl.listplus.api.user.model.User;
import cl.listplus.api.user.repository.UserMapper;
import cl.listplus.api.user.repository.UserRepository;
import cl.listplus.api.user.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User retrieveUser(Specification<User> specification) {
        return userRepository.findAll(specification)
                .stream().findFirst()
                .orElseThrow(() -> new UserServiceException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public void createUser(UserRequest userRequest) {
        userRepository.findByEmailOrUsername(userRequest.getEmail(), userRequest.getUsername()).stream()
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresentOrElse(
                        user -> { throw new UserServiceException(HttpStatus.CONFLICT, "User already exists"); },
                        () -> save(userRequest)
                );
    }

    @Override
    public void updateUser(UserRequest userRequest) {
        userRepository.findById(userRequest.getId())
                .ifPresentOrElse(
                        user -> save(userRequest),
                        () -> { throw new UserServiceException(HttpStatus.NOT_FOUND, "User does not exist"); }
                );
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        user -> userRepository.deleteById(user.getId()),
                        () -> { throw new UserServiceException(HttpStatus.NOT_FOUND, "User does not exist"); }
                );
    }

    private void save(UserRequest userRequest) {
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(userRequest.getCreatedOn())) {
            userRequest.setCreatedOn(now);
            userRequest.setActive(true);
        }
        userRequest.setLastUpdatedOn(now);
        userRepository.save(userMapper.map(userRequest));
    }
}

