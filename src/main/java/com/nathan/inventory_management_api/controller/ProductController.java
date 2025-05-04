package com.nathan.inventory_management_api.controller;

import com.nathan.inventory_management_api.dto.ProductDto;
import com.nathan.inventory_management_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for product operations.
 * Provides endpoints for CRUD operations on products and for checking low stock products.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing inventory products")
public class ProductController {

    /**
     * Service for product operations.
     * Injected through constructor (RequiredArgsConstructor).
     */
    private final ProductService productService;

    /**
     * Retrieves all products.
     *
     * @return ResponseEntity containing list of products
     */
    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns a list of all products in the inventory"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<List<ProductDto.Response>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Retrieves a specific product by ID.
     *
     * @param id The ID of the product to retrieve
     * @return ResponseEntity containing the product details
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Returns a single product by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ProductDto.Response> getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Creates a new product.
     *
     * @param productRequest The product data
     * @return ResponseEntity containing the created product
     */
    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product in the inventory"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ProductDto.Response> createProduct(
            @Parameter(description = "Product data for creation", required = true,
                    schema = @Schema(implementation = ProductDto.Request.class))
            @Valid @RequestBody ProductDto.Request productRequest) {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update
     * @param productRequest The updated product data
     * @return ResponseEntity containing the updated product
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a product",
            description = "Updates an existing product by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ProductDto.Response> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated product data", required = true,
                    schema = @Schema(implementation = ProductDto.Request.class))
            @Valid @RequestBody ProductDto.Request productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    /**
     * Deletes a product.
     *
     * @param id The ID of the product to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves products that are low in stock.
     *
     * @return ResponseEntity containing list of low stock products
     */
    @GetMapping("/low-stock")
    @Operation(
            summary = "Get low stock products",
            description = "Returns all products that are below their low stock threshold"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Low stock products retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.Response.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<List<ProductDto.Response>> getLowStockProducts() {
        return ResponseEntity.ok(productService.getLowStockProducts());
    }
}

