package cl.listplus.api.gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "status", "message"})
public class UserResponse {

    @JsonIgnore
    private HttpStatus httpStatus;

    private String message;

    @JsonProperty("code")
    public int getCode() {
        return this.httpStatus.value();
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.httpStatus.name();
    }
}
