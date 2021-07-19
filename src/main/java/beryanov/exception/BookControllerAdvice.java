package beryanov.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;

@ControllerAdvice
@Slf4j
public class BookControllerAdvice {
    private record StructuredExceptionInfo(String message, Timestamp timestamp) {}

    private StructuredExceptionInfo getStructuredInfo(Exception exception) {
        return new StructuredExceptionInfo(exception.getMessage(), new Timestamp(System.currentTimeMillis()));
    }

    private void logException(Exception exception) {
        log.error("Возникло исключение: ", exception);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StructuredExceptionInfo> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        logException(exception);

        return ResponseEntity.internalServerError().body(getStructuredInfo(exception));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StructuredExceptionInfo> handleRuntimeException(RuntimeException exception) {
        logException(exception);

        return ResponseEntity.internalServerError().body(getStructuredInfo(exception));
    }
}
