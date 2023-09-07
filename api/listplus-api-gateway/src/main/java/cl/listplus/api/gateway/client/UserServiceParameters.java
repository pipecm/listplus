package cl.listplus.api.gateway.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "client.user-service")
public class UserServiceParameters {
    private String url = "not-set";
    private long timeout = 10;
}
