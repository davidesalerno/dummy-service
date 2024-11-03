package com.acme.dummyservice.config;

import com.acme.dummyservice.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestErrorHandling extends ResponseEntityExceptionHandler {

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(BAD_REQUEST);
        errorDTO.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        errorDTO.setDebugMessage(ex.getMessage());
        return buildResponseEntity(errorDTO);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler({RuntimeException.class, Throwable.class, Exception.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex,
                                                                      WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(INTERNAL_SERVER_ERROR);
        errorDTO.setMessage("An unexpected error occurred trying to serve your request.");
        errorDTO.setDebugMessage(ex.getMessage());
        log.error(ex.getMessage());
        return buildResponseEntity(errorDTO);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
        return new ResponseEntity<>(errorDTO, errorDTO.getStatus());
    }

}