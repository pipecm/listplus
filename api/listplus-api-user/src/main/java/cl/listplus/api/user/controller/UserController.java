package cl.listplus.api.user.controller;

import cl.listplus.api.user.exception.UserServiceException;
import cl.listplus.api.user.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Stream;

import static cl.listplus.api.user.repository.UserSpecifications.hasEmail;
import static cl.listplus.api.user.repository.UserSpecifications.hasUsername;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> retrieveUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.retrieveUser(hasUsername(username)));
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
