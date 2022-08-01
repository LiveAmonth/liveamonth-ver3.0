package teamproject.lam_server.exception;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import teamproject.lam_server.global.dto.CustomResponse;

import java.util.List;
import java.util.stream.Collectors;

import static teamproject.lam_server.exception.ErrorCode.DUPLICATED_RESOURCE;
import static teamproject.lam_server.exception.ErrorCode.ILLEGAL_ARGUMENT;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(value = LiveamonthException.class)
    public final ResponseEntity<?> liveamonthException(LiveamonthException e) {
        return CustomResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity<?> handleIllegalArgumentExceptions() {
        return CustomResponse.fail(ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    public final ResponseEntity<?> handleDataException() {
        return CustomResponse.fail(DUPLICATED_RESOURCE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return CustomResponse.validationFail(createValidationDetails(ex));
    }

    private List<ValidationResponse> createValidationDetails(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream()
                .map(fieldError -> new ValidationResponse(fieldError, messageSource))
                .collect(Collectors.toList());
    }

}
