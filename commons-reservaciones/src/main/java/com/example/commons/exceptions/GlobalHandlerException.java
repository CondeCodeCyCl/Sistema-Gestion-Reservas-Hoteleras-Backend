package com.example.commons.exceptions;

import com.example.commons.dto.ErrorResponse;

import feign.FeignException;
import feign.RetryableException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintviolationException(ConstraintViolationException e){
        log.error("Violacion de restricción: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        "Violación de restricción: " + e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String mensaje = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación en los datos enviados");
        log.error("Error de validación de argumentos: {}", mensaje);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mensaje));
    }

    /*@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Error en la petición: {}", e.getMessage());
        return ResponseEntity.badRequest().body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
        );
    }*/
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        
        // Creamos un JSON clave-valor limpio
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("message", ex.getMessage()); 
        
        // Retornamos el JSON con un código 400 (Bad Request)
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        log.warn("No se encontró recurso: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("No se encontró recurso: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontradoException(RecursoNoEncontradoException e) {
        log.warn("No se encontró el recurso: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
    @ExceptionHandler(EntidadRelacionadaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadRelacionadaException(EntidadRelacionadaException e) {
        log.warn("Error al eliminar un recurso: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> IllegalStateException(IllegalStateException e) {
        log.warn("Estado no válido: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error("Error interno del servidor: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error interno del servidor. Por favor, contacte al administrador."));
    }
    
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleGenericFeignException(FeignException e) {
        log.error("Error en la comunicación Feign: " + e.getMessage());

        int status = e.status() > 0 ? e.status() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = switch (status) {
            case 400 -> "Solicitud incorrecta al servicio remoto.";
            case 401 -> "No autorizado para acceder al servicio remoto.";
            case 403 -> "Acceso prohibido al servicio remoto.";
            case 404 -> "Recurso no encontrado en el servicio remoto.";
            case 409 -> "Conflicto: el recurso tiene dependencias activas.";
            case 503 -> "Servicio remoto no disponible.";
            default -> "Error al comunicarse con el servicio remoto.";
        };
        ErrorResponse response = new ErrorResponse(status, message);

        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ErrorResponse> handleRetryable(RetryableException e) {
    	log.error("Servicio remoto no disponible o no responde: " + e.getMessage());
    	ErrorResponse response = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(),
    			"Servicio remoto no disponible o no responde");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
    
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException e) {
        log.error("Error al leer el JSON (probablemente letras en lugar de números): {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 
                        "El formato de los datos enviados es inválido o contiene tipos de datos incorrectos (ej. letras en lugar de números)."));
    }
    
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException e) {
        log.error("Tipo de dato incorrecto en la URL: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 
                        "El parámetro '" + e.getName() + "' en la URL tiene un tipo de dato inválido."));
    }
    
    @ExceptionHandler(DatosDuplicadosException.class)
    public ResponseEntity<ErrorResponse> handleDatoDuplicadoException(DatosDuplicadosException e) {
        log.warn("Dato duplicado: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }
    
}
