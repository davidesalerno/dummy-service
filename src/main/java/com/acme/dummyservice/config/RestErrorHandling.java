package com.acme.dummyservice.config;

import com.acme.dummyservice.dto.ErrorDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
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
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
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
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex,
                                                                      WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(INTERNAL_SERVER_ERROR);
        errorDTO.setMessage("An unexpected error occurred trying to serve your request.");
        errorDTO.setDebugMessage(ex.getMessage());
        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex,
                                                            WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(INTERNAL_SERVER_ERROR);
        errorDTO.setMessage("An unexpected error occurred trying to serve your request.");
        errorDTO.setDebugMessage(ex.getMessage());
        return buildResponseEntity(errorDTO);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
        return new ResponseEntity<>(errorDTO, errorDTO.getStatus());
    }

}