package cl.listplus.api.user.controller;

import cl.listplus.api.user.exception.UserServiceException;
import cl.listplus.api.user.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static cl.listplus.api.user.repository.UserSpecifications.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveUserById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.retrieveUser(hasId(id)));
    }

    @GetMapping
    public ResponseEntity<?> retrieveUserByUsernameOrEmail(@RequestParam(required = false) String username,
                                                           @RequestParam(required = false) String email) {
        if (Stream.of(username, email).allMatch(Objects::isNull)) {
            throw new UserServiceException(HttpStatus.BAD_REQUEST, "Please provide an username or email");
        }
        return ResponseEntity.ok().body(userService.retrieveUser(Specification.anyOf(hasUsername(username), hasEmail(email))));
    }
}
