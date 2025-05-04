package com.nathan.inventory_management_api.exception;

/**
 * Exception thrown when a requested resource is not found.
 *
 * This exception is used when a client requests a resource (like a product)
 * that doesn't exist in the database. It extends RuntimeException, making it
 * an unchecked exception.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new ResourceNotFoundException with a custom message.
     *
     * @param message Details about the resource that wasn't found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a new ResourceNotFoundException with a custom message and cause.
     *
     * @param message Details about the resource that wasn't found
     * @param cause The underlying cause of the exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

