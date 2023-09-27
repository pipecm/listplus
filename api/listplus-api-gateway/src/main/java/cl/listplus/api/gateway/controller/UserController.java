package cl.listplus.api.gateway.controller;

import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.domain.UserResponse;
import cl.listplus.api.gateway.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> retrieveUser(@PathVariable UUID id) {
        if (Objects.isNull(id)) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return userService.retrieveUser(id)
                .map(userResponse -> ResponseEntity.ok().body(userResponse));
    }

    @GetMapping
    public Mono<ResponseEntity<UserResponse>> retrieveUser(@RequestParam(required = false) String username,
                                                           @RequestParam(required = false) String email) {
        if (Stream.of(username, email).allMatch(Objects::isNull)) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return userService.retrieveUser(UserRequest.builder()
                        .username(username)
                        .email(email)
                        .build())
                .map(userResponse -> ResponseEntity.ok().body(userResponse));
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest)
                .map(userResponse -> ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable UUID id, @RequestBody UserRequest userRequest) {
        userRequest.setId(id);

        return userService.updateUser(userRequest)
                .map(userResponse -> ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(UserRequest.builder().id(id).build())
                .map(userResponse -> ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse));
    }
}
