package io.github.siegjor.todomanager.exception;

import io.github.siegjor.todomanager.utils.MessageKeys;
import io.github.siegjor.todomanager.utils.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageUtil messageUtil;

    public GlobalExceptionHandler(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldError(
                        error.getField(),
                        messageUtil.getMessage(error.getDefaultMessage()),
                        error.getCode()
                ))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                messageUtil.getMessage(MessageKeys.VALIDATION_FAILED),
                HttpStatus.BAD_REQUEST.value(),
                ErrorCodes.VALIDATION_ERROR,
                OffsetDateTime.now(),
                fieldErrors,
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                messageUtil.getMessage(ex.getMessage()),
                HttpStatus.CONFLICT.value(),
                ErrorCodes.USERNAME_TAKEN,
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                messageUtil.getMessage(ex.getMessage()),
                HttpStatus.CONFLICT.value(),
                ErrorCodes.EMAIL_ALREADY_REGISTERED,
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                messageUtil.getMessage(ex.getMessage()),
                HttpStatus.NOT_FOUND.value(),
                ErrorCodes.EMAIL_ALREADY_REGISTERED,
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
