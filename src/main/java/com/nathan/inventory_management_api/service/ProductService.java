package com.nathan.inventory_management_api.service;

import com.nathan.inventory_management_api.dto.ProductDto;
import com.nathan.inventory_management_api.exception.ResourceNotFoundException;
import com.nathan.inventory_management_api.model.Product;
import com.nathan.inventory_management_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Product operations.
 *
 * This class contains the business logic for creating, retrieving,
 * updating, and deleting products, as well as checking for low stock.
 * It acts as an intermediary between the controller and the repository.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    /**
     * Repository for Product entity operations.
     * Injected through constructor (RequiredArgsConstructor from Lombok).
     */
    private final ProductRepository productRepository;

    /**
     * Retrieves all products from the database.
     *
     * @return List of products converted to DTO responses
     */
    public List<ProductDto.Response> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id The ID of the product to retrieve
     * @return The product response DTO
     * @throws ResourceNotFoundException if the product is not found
     */
    public ProductDto.Response getProductById(Long id) {
        Product product = findProductById(id);
        return mapToProductResponse(product);
    }

    /**
     * Creates a new product from the request data.
     *
     * @param productRequest The product data to create
     * @return The created product as a response DTO
     */
    @Transactional
    public ProductDto.Response createProduct(ProductDto.Request productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    /**
     * Updates an existing product with new data.
     *
     * @param id The ID of the product to update
     * @param productRequest The new product data
     * @return The updated product as a response DTO
     * @throws ResourceNotFoundException if the product is not found
     */
    @Transactional
    public ProductDto.Response updateProduct(Long id, ProductDto.Request productRequest) {
        Product product = findProductById(id);
        updateProductFromRequest(product, productRequest);

        Product updatedProduct = productRepository.save(product);
        return mapToProductResponse(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete
     * @throws ResourceNotFoundException if the product is not found
     */
    @Transactional
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    /**
     * Retrieves all products that are low in stock.
     *
     * @return List of low stock products as response DTOs
     */
    public List<ProductDto.Response> getLowStockProducts() {
        return productRepository.findLowStockProducts().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to find a product by ID.
     *
     * @param id The ID of the product to find
     * @return The found product
     * @throws ResourceNotFoundException if the product is not found
     */
    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    /**
     * Updates a product entity with data from a request DTO.
     *
     * @param product The product entity to update
     * @param request The request DTO containing the new data
     */
    private void updateProductFromRequest(Product product, ProductDto.Request request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setLowStockThreshold(request.getLowStockThreshold());
        product.setSku(request.getSku());
        product.setCategory(request.getCategory());

        // Update the low stock status based on current stock and threshold
        product.updateLowStockStatus();
    }

    /**
     * Maps a Product entity to a response DTO.
     *
     * @param product The product entity to map
     * @return The mapped product response DTO
     */
    private ProductDto.Response mapToProductResponse(Product product) {
        ProductDto.Response response = new ProductDto.Response();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setLowStockThreshold(product.getLowStockThreshold());
        response.setIsLowStock(product.getIsLowStock());
        response.setSku(product.getSku());
        response.setCategory(product.getCategory());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}

