package cl.listplus.api.user.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    protected ResponseEntity<?> handleUserServiceException(UserServiceException exception, WebRequest request) {
        return handleExceptionInternal(exception, new UserServiceErrorResponse(exception),
                new HttpHeaders(), exception.getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleGeneralException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, new UserServiceErrorResponse(exception),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
