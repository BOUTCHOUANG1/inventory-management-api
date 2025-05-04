package com.nathan.inventory_management_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Objects for Product entities.
 *
 * This class contains nested static classes for different DTOs related to the Product entity:
 * - Request: Used for incoming data when creating or updating a product
 * - Response: Used for outgoing data when returning product information
 *
 * Using separate DTOs for requests and responses allows for different validation rules
 * and data structures for incoming and outgoing data.
 */
public class ProductDto {

    /**
     * DTO for incoming product creation or update requests.
     * Contains validation constraints to ensure valid data.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        /**
         * Name of the product.
         * Must not be blank and must be between 2 and 100 characters.
         */
        @NotBlank(message = "Product name is required")
        @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
        private String name;

        /**
         * Description of the product.
         * Optional field that provides additional information about the product.
         */
        private String description;

        /**
         * Price of the product.
         * Must be greater than 0.
         */
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        private BigDecimal price;

        /**
         * Current stock quantity of the product.
         * Must be a non-negative number.
         */
        @Min(value = 0, message = "Stock quantity cannot be negative")
        private Integer stockQuantity;

        /**
         * Threshold for low stock alert.
         * When stockQuantity falls below this value, the product is considered low in stock.
         * Must be at least 1.
         */
        @Min(value = 1, message = "Low stock threshold must be at least 1")
        private Integer lowStockThreshold = 5;

        /**
         * SKU (Stock Keeping Unit) - a unique identifier for the product.
         * Optional field.
         */
        private String sku;

        /**
         * Category or type of the product.
         * Optional field for grouping products.
         */
        private String category;
    }

    /**
     * DTO for outgoing product responses.
     * Contains all relevant product information for clients.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        /**
         * Unique identifier for the product.
         */
        private Long id;

        /**
         * Name of the product.
         */
        private String name;

        /**
         * Description of the product.
         */
        private String description;

        /**
         * Price of the product.
         */
        private BigDecimal price;

        /**
         * Current stock quantity of the product.
         */
        private Integer stockQuantity;

        /**
         * Threshold for low stock alert.
         */
        private Integer lowStockThreshold;

        /**
         * Flag indicating whether the product is low in stock.
         */
        private Boolean isLowStock;

        /**
         * SKU (Stock Keeping Unit) of the product.
         */
        private String sku;

        /**
         * Category or type of the product.
         */
        private String category;

        /**
         * Timestamp when the product was created.
         */
        private LocalDateTime createdAt;

        /**
         * Timestamp when the product was last updated.
         */
        private LocalDateTime updatedAt;
    }
}