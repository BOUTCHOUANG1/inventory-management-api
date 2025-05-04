package com.nathan.inventory_management_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a product in the inventory.
 * This class maps to the 'products' table in the database.
 *
 * The class uses various annotations:
 * - JPA annotations (@Entity, @Table, etc.) for ORM mapping
 * - Validation annotations (@NotBlank, @Min, etc.) for input validation
 * - Lombok annotations (@Data, etc.) to reduce boilerplate code
 * - Hibernate annotations for timestamp management
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Unique identifier for the product.
     * Auto-generated as an identity column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the product.
     * Must not be blank and must be between 2 and 100 characters.
     */
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    /**
     * Description of the product.
     * Stored as TEXT to accommodate longer descriptions.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Price of the product.
     * Must be a positive number (greater than 0).
     */
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Current stock quantity of the product.
     * Must be a non-negative number (0 or greater).
     */
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Column(nullable = false)
    private Integer stockQuantity;

    /**
     * Threshold for low stock alert.
     * When stockQuantity falls below this value, a low stock alert is triggered.
     * Default value is 5.
     */
    @Min(value = 1, message = "Low stock threshold must be at least 1")
    @Column(nullable = false)
    private Integer lowStockThreshold = 5;

    /**
     * Flag indicating whether the product is currently low in stock.
     * This is calculated based on stockQuantity and lowStockThreshold.
     */
    @Column(nullable = false)
    private Boolean isLowStock = false;

    /**
     * SKU (Stock Keeping Unit) - a unique identifier for the product in inventory.
     */
    private String sku;

    /**
     * Category or type of the product.
     */
    private String category;

    /**
     * Timestamp when the product was created.
     * Automatically generated and cannot be updated.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the product was last updated.
     * Automatically updated when the product is modified.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Updates the low stock status based on the current stock quantity
     * and the low stock threshold.
     *
     * This method should be called whenever the stock quantity changes.
     */
    @PrePersist
    @PreUpdate
    public void updateLowStockStatus() {
        this.isLowStock = this.stockQuantity <= this.lowStockThreshold;
    }
}
