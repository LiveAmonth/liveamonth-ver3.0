package teamproject.lam_server.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import teamproject.lam_server.app.member.exception.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity(createExceptionResponse(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AlreadyDropUserException.class, NormalUserDeleteException.class})
    public final ResponseEntity<Object> handleAlreadyDropUserException(Exception ex, WebRequest request) {
        return new ResponseEntity(createExceptionResponse(ex, request), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({ValidTokenException.class,CorrespondException.class, UserNotFoundException.class})
    public final ResponseEntity<Object> handleTokenException(Exception ex, WebRequest request) {
        return new ResponseEntity(createExceptionResponse(ex, request), HttpStatus.BAD_REQUEST);
    }

    private ExceptionResponse createExceptionResponse(Exception ex, WebRequest request) {
        return new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),"Validation Failed",createValidationDetails(ex));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    private List<ValidationResponse> createValidationDetails(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream()
                .map(fieldError -> new ValidationResponse(fieldError, messageSource))
                .collect(Collectors.toList());
    }


}
