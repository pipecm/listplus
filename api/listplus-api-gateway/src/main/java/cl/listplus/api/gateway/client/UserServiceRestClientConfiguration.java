package cl.listplus.api.gateway.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UserServiceRestClientConfiguration {

    private UserServiceParameters userServiceParameters;

    public UserServiceRestClientConfiguration(UserServiceParameters userServiceParameters) {
        this.userServiceParameters = userServiceParameters;
    }

    @Bean
    public WebClient userServiceWebClient() {
        return WebClient.builder()
                .baseUrl(userServiceParameters.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
