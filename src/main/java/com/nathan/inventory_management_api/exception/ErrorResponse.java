package com.nathan.inventory_management_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standardized error response format for API errors.
 *
 * This class provides a consistent structure for all error responses
 * from the API, including timestamps, status codes, error messages,
 * and additional details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * The timestamp when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * The HTTP status code of the response.
     */
    private int status;

    /**
     * A short error message describing what went wrong.
     */
    private String message;

    /**
     * The path of the request that caused the error.
     */
    private String path;

    /**
     * Detailed error information (optional).
     */
    private String details;

    /**
     * Creates a new ErrorResponse with the current timestamp.
     *
     * @param status HTTP status code
     * @param message Error message
     * @param path Request path
     * @param details Additional error details
     */
    public ErrorResponse(int status, String message, String path, String details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.details = details;
    }

    /**
     * Creates a new ErrorResponse with the current timestamp and no details.
     *
     * @param status HTTP status code
     * @param message Error message
     * @param path Request path
     */
    public ErrorResponse(int status, String message, String path) {
        this(status, message, path, null);
    }
}

