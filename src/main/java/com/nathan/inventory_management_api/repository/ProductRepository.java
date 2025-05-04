package com.nathan.inventory_management_api.repository;

import com.nathan.inventory_management_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Product entities.
 *
 * This interface extends JpaRepository to provide basic CRUD operations
 * and adds custom query methods for specific business requirements.
 * Spring Data JPA automatically implements these methods at runtime.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds all products that are low in stock (stockQuantity <= lowStockThreshold).
     *
     * @return List of products that are low in stock
     */
    @Query("SELECT p FROM Product p WHERE p.isLowStock = true")
    List<Product> findLowStockProducts();

    /**
     * Finds products by their name, using case-insensitive partial matching.
     *
     * @param name The product name to search for
     * @return List of products matching the name pattern
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Finds products by their category, using case-insensitive exact matching.
     *
     * @param category The category to search for
     * @return List of products in the specified category
     */
    List<Product> findByCategoryIgnoreCase(String category);

    /**
     * Finds a product by its SKU (Stock Keeping Unit).
     *
     * @param sku The SKU to search for
     * @return The product with the specified SKU, or null if not found
     */
    Product findBySku(String sku);
}

