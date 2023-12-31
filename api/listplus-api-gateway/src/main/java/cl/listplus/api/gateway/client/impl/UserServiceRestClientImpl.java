package cl.listplus.api.gateway.client.impl;

import cl.listplus.api.gateway.client.UserServiceParameters;
import cl.listplus.api.gateway.client.UserServiceRestClient;
import cl.listplus.api.gateway.domain.User;
import cl.listplus.api.gateway.domain.UserRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceRestClientImpl implements UserServiceRestClient {

    private static final String USER_BY_ID_ENDPOINT = "/users/%s";
    private static final String USERS_BY_FILTERS_ENDPOINT = "/users?%s";
    private static final String USERNAME_FILTER_URI = "username=%s";
    private static final String EMAIL_FILTER_URI = "email=%s";
    private static final String CHARACTER_AMPERSAND = "&";

    private final WebClient userServiceWebClient;
    private final UserServiceParameters userServiceParameters;

    public UserServiceRestClientImpl(WebClient userServiceWebClient, UserServiceParameters userServiceParameters) {
        this.userServiceWebClient = userServiceWebClient;
        this.userServiceParameters = userServiceParameters;
    }

    @Override
    public Mono<User> retrieveUser(UUID id) {
        return userServiceWebClient.get()
                .uri(String.format(USER_BY_ID_ENDPOINT, id.toString()))
                .retrieve()
                .bodyToMono(User.class)
                .timeout(Duration.ofSeconds(userServiceParameters.getTimeout()));
    }

    @Override
    public Mono<User> retrieveUser(UserRequest userRequest) {
        return userServiceWebClient.get()
                .uri(buildRetrieveUserUri(userRequest))
                .retrieve()
                .bodyToMono(User.class)
                .timeout(Duration.ofSeconds(userServiceParameters.getTimeout()));
    }

    private String buildRetrieveUserUri(UserRequest userRequest) {
        return Map.of(UserRequest.Fields.username,
                        Optional.ofNullable(userRequest).map(UserRequest::getUsername).orElse(StringUtils.EMPTY),
                        UserRequest.Fields.email,
                        Optional.ofNullable(userRequest).map(UserRequest::getEmail).orElse(StringUtils.EMPTY))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isBlank())
                .map(entry -> {
                    if (UserRequest.Fields.username.equals(entry.getKey())) {
                        return String.format(USERNAME_FILTER_URI, entry.getValue());
                    } else if (UserRequest.Fields.email.equals(entry.getKey())) {
                        return String.format(EMAIL_FILTER_URI, entry.getValue());
                    }
                    return StringUtils.EMPTY;
                })
                .reduce((f1, f2) -> String.join(CHARACTER_AMPERSAND, f1, f2))
                .map(filters -> String.format(USERS_BY_FILTERS_ENDPOINT, filters))
                .orElse(StringUtils.EMPTY);
    }
}

