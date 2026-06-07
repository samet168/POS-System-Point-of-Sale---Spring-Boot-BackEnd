package com.example.StoreMSI.Util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private int status;
    private LocalDateTime timestamp;

    // =========================
    // OK RESPONSE
    // =========================
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Success", data, 200, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(true, message, null, 200, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data, 200, LocalDateTime.now());
    }


    // =========================
    // CREATE RESPONSE (201)
    // =========================
    public static <T> ApiResponse<T> create(T data) {
        return new ApiResponse<>(true, "Created successfully", data, 201, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> create(String message, T data) {
        return new ApiResponse<>(true, message, data, 201, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> create(String message) {
        return new ApiResponse<>(true, message, null, 201, LocalDateTime.now());
    }

    // =========================
    // ERROR RESPONSE
    // =========================
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, 400, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message, int status) {
        return new ApiResponse<>(false, message, null, status, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data, 400, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message, int status, T data) {
        return new ApiResponse<>(false, message, data, status, LocalDateTime.now());
    }
}