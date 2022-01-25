package teamproject.lam_server.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import teamproject.lam_server.app.user.exception.AlreadyDropUserException;
import teamproject.lam_server.app.user.exception.NormalUserDeleteException;
import teamproject.lam_server.app.user.exception.UserNotFoundException;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity(createExceptionResponse(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity(createExceptionResponse(ex, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyDropUserException.class, NormalUserDeleteException.class})
    public final ResponseEntity<Object> handleAlreadyDropUserException(Exception ex, WebRequest request) {
        return new ResponseEntity(createExceptionResponse(ex, request), HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ExceptionResponse createExceptionResponse(Exception ex, WebRequest request) {
        return new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    }
}
