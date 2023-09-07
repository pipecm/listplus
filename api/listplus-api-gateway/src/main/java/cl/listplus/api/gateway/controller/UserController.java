package cl.listplus.api.gateway.controller;

import cl.listplus.api.gateway.domain.User;
import cl.listplus.api.gateway.domain.UserRequest;
import cl.listplus.api.gateway.domain.UserResponse;
import cl.listplus.api.gateway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String USER_URI_PATH = "/{username}";

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Mono<ResponseEntity<User>> retrieveUser(@RequestParam(required = false) String username,
                                                   @RequestParam(required = false) String email) {
        if (Stream.of(username, email).allMatch(Objects::isNull)) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return userService.retrieveUser(UserRequest.builder()
                        .username(username)
                        .email(email)
                        .build())
                .map(user -> ResponseEntity.ok().body(user));
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        URI userCreatedUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(USER_URI_PATH)
                .buildAndExpand(userRequest.getUsername())
                .toUri();

        return userService.createUser(userRequest)
                .map(response -> {
                    if (HttpStatus.CREATED.equals(response.getHttpStatus())) {
                        return ResponseEntity.created(userCreatedUri).body(response);
                    } else {
                        return ResponseEntity.status(response.getHttpStatus()).body(response);
                    }
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Void>> updateUser(@PathVariable UUID id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest)
                .map(response -> ResponseEntity.ok().body(response));
    }
}
