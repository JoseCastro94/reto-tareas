package com.app.reto.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundExceptionHandler(ResourceNotFoundException ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return errorMessage;
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage invalidDataExceptionHandler(InvalidDataException ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return errorMessage;
    }

    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBindException(BindingResult bindingResult, HttpServletRequest request) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = "Se encontraron errores en los campos de la solicitud. Por favor, corríjalos y vuelva a intentarlo.";

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",  
                errorMessage,  
                errors,
                request.getRequestURI(),  
                LocalDateTime.now()
          
        );
    }
    
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String errorDescription = "El cuerpo de la solicitud no se puede leer o está mal formado. Asegúrese de enviar el cuerpo de la solicitud en formato JSON.";

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorDescription,  
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }

    // Manejador para recursos no encontrados
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                "La URL solicitada no existe. Por favor, verifique la solicitud.",
                ex.getRequestURL(), 
                LocalDateTime.now()  
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String errorMessage = String.format("El parámetro '%s' no es válido. Se esperaba un valor de tipo '%s'.", 
                ex.getName(), ex.getRequiredType().getSimpleName());
        
        ErrorMessage errorResponse = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errorMessage,
                request.getRequestURI(),
                LocalDateTime.now()  
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleCustomBadCredentialsException(CustomBadCredentialsException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            "Bad Credentials",
            ex.getCustomMessage(), 
            request.getDescription(false),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
