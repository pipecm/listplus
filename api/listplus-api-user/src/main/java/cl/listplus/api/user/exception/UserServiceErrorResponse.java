package cl.listplus.api.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserServiceErrorResponse {
    private int code;
    private String status;
    private String message;

    public UserServiceErrorResponse(UserServiceException exception) {
        this.code = exception.getStatus().value();
        this.status = exception.getStatus().name();
        this.message = exception.getMessage();
    }

    public UserServiceErrorResponse(Throwable throwable) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.name();
        this.message = throwable.getMessage();
    }
}
