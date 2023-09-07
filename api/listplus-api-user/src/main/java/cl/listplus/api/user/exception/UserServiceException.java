package cl.listplus.api.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserServiceException extends RuntimeException {

    private HttpStatus status;

    public UserServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public UserServiceException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
