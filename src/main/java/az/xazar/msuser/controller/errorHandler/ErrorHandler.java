package az.xazar.msuser.controller.errorHandler;

import az.xazar.msuser.model.ErrorDto;
import az.xazar.msuser.model.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static az.xazar.msuser.model.exception.ErrorCodes.NOT_FOUND;
import static az.xazar.msuser.model.exception.ErrorCodes.UNEXPECTED_EXCEPTION;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ErrorHandler.class.getName());

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex,
                                                              WebRequest webRequest) {
        logger.info(ex.toString());

        return handleExceptionInternal(ex, ErrorDto.builder()
                        .code(NOT_FOUND)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex,
                                                     WebRequest webRequest) {
        logger.info(ex.getMessage());

        return handleExceptionInternal(ex, ErrorDto.builder()
                        .code(UNEXPECTED_EXCEPTION)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}
