package cl.listplus.api.gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "status", "message", "data"})
public class UserResponse {

    @JsonIgnore
    private HttpStatus httpStatus;

    private String message;

    private List<User> data;

    @JsonProperty("code")
    public int getCode() {
        return this.httpStatus.value();
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.httpStatus.name();
    }

    public UserResponse(List<User> data) {
        this.httpStatus = HttpStatus.OK;
        this.message = "success";
        this.data = data;
    }

    public UserResponse(User user) {
        this(List.of(user));
    }

    public UserResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
