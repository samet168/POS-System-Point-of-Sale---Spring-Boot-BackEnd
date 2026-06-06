package com.example.StoreMSI.Exception;


import com.example.StoreMSI.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    // =======================
    // 404 NOT FOUND
    // =======================
    @ExceptionHandler(ResourceNotException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotException ex) {

        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    // =======================
    // VALIDATION ERROR (400)
    // =======================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        Map<String, Object> data = new HashMap<>();
        data.put("errors", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        "Validation failed",
                        HttpStatus.BAD_REQUEST.value(),
                        data
                ));
    }

    // =======================
    // GLOBAL ERROR (500)
    // =======================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {

        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }
}