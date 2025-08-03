# Inventory Management API

A RESTful API for managing an inventory of products with stock tracking built with Spring Boot.

## Overview

This API allows users to:
- Create, read, update, and delete products
- Track stock quantities for products
- Set low stock thresholds for each product
- Receive alerts for products that are low in stock

## Technology Stack

- **Java 21**
- **Spring Boot 3.4.5** - Framework for building production-ready applications
- **Spring Data JPA** - Data persistence using JPA
- **PostgreSQL** - Database to store product information
- **Swagger/OpenAPI** - API documentation
- **Lombok** - Reduces boilerplate code
- **Maven** - Build and dependency management

## Project Structure

```
src/main/java/com/example/inventorymanagementapi/
├── controller       # REST Controllers
├── model            # Entity classes
├── repository       # Data repositories
├── service          # Business logic
├── dto              # Data Transfer Objects
├── exception        # Custom exceptions and handlers
└── config           # Configuration classes
```

## Model

### Product
- `id`: Long (Primary Key)
- `name`: String (Required, 2-100 characters)
- `description`: String (Optional)
- `price`: BigDecimal (Required, greater than 0)
- `stockQuantity`: Integer (Required, non-negative)
- `lowStockThreshold`: Integer (Default: 5)
- `isLowStock`: Boolean (calculated based on stockQuantity and lowStockThreshold)
- `sku`: String (Optional)
- `category`: String (Optional)
- `createdAt`: LocalDateTime (Auto-generated)
- `updatedAt`: LocalDateTime (Auto-updated)

## API Endpoints

| Method | URL                        | Description                   | Status Codes               |
|--------|-----------------------------|-------------------------------|-----------------------------|
| GET    | /api/products               | Get all products              | 200                         |
| GET    | /api/products/{id}          | Get product by ID             | 200, 404                    |
| POST   | /api/products               | Create a new product          | 201, 400                    |
| PUT    | /api/products/{id}          | Update a product              | 200, 400, 404              |
| DELETE | /api/products/{id}          | Delete a product              | 204, 404                    |
| GET    | /api/products/low-stock     | Get all low stock products    | 200                         |

## Setup and Running

### Prerequisites
- Java 17 or higher
- Maven
- PostgreSQL database

### Database Setup
1. Create a PostgreSQL database named `inventory_management_api`
2. Update the `application.properties` file with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_management_api
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Building the Project
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run
```

The application will be available at: http://localhost:8080

## Testing the API with Swagger UI

The API is documented using Swagger, which provides an interactive interface to explore and test the endpoints.

### Accessing Swagger UI

1. Start the application using the instructions above
2. Open a web browser and navigate to: http://localhost:8080/swagger-ui.html

### Testing Endpoints

#### 1. Create a Product (POST /api/products)

1. Click on the `Product Management` section to expand it
2. Find the `POST /api/products` endpoint and click to expand it
3. Click the "Try it out" button
4. Enter the product data in the Request Body field:
   ```json
   {
     "name": "Smartphone XYZ",
     "description": "Latest model with 6.5 inch display",
     "price": 799.99,
     "stockQuantity": 10,
     "lowStockThreshold": 3,
     "sku": "PHN-XYZ-001",
     "category": "Electronics"
   }
   ```
5. Click "Execute"
6. You should see a 201 Created response with the created product details, including an ID and timestamps

#### 2. Get All Products (GET /api/products)

1. Find the `GET /api/products` endpoint and click to expand it
2. Click "Try it out"
3. Click "Execute"
4. You'll see a list of all products, including the one you just created

#### 3. Get a Specific Product (GET /api/products/{id})

1. Find the `GET /api/products/{id}` endpoint and click to expand it
2. Click "Try it out"
3. Enter the ID of the product you created (as shown in the response from step 1)
4. Click "Execute"
5. You'll see the details of the product with the specified ID

#### 4. Update a Product (PUT /api/products/{id})

1. Find the `PUT /api/products/{id}` endpoint and click to expand it
2. Click "Try it out"
3. Enter the ID of the product you want to update
4. Update the product data in the Request Body field:
   ```json
   {
     "name": "Smartphone XYZ",
     "description": "Latest model with 6.5 inch display and improved camera",
     "price": 749.99,
     "stockQuantity": 8,
     "lowStockThreshold": 3,
     "sku": "PHN-XYZ-001",
     "category": "Electronics"
   }
   ```
5. Click "Execute"
6. You'll see a 200 OK response with the updated product details

#### 5. Test Low Stock Alert (PUT /api/products/{id})

1. Find the `PUT /api/products/{id}` endpoint again
2. Click "Try it out"
3. Enter the ID of the product you created
4. Update the stock quantity to be lower than the low stock threshold:
   ```json
   {
     "name": "Smartphone XYZ",
     "description": "Latest model with 6.5 inch display and improved camera",
     "price": 749.99,
     "stockQuantity": 2,
     "lowStockThreshold": 3,
     "sku": "PHN-XYZ-001",
     "category": "Electronics"
   }
   ```
5. Click "Execute"
6. You'll see a 200 OK response with the updated product, and the `isLowStock` field should now be `true`

#### 6. Get Low Stock Products (GET /api/products/low-stock)

1. Find the `GET /api/products/low-stock` endpoint and click to expand it
2. Click "Try it out"
3. Click "Execute"
4. You'll see a list of products that are below their low stock threshold, including the one you just updated

#### 7. Delete a Product (DELETE /api/products/{id})

1. Find the `DELETE /api/products/{id}` endpoint and click to expand it
2. Click "Try it out"
3. Enter the ID of the product you want to delete
4. Click "Execute"
5. You should see a 204 No Content response

## API Testing with cURL

### Create a Product
```bash
curl -X POST 'http://localhost:8080/api/products' \
-H 'Content-Type: application/json' \
-d '{
    "name": "Wireless Headphones",
    "description": "Noise cancelling wireless headphones",
    "price": 159.99,
    "stockQuantity": 15,
    "lowStockThreshold": 5,
    "sku": "HDN-001",
    "category": "Audio"
}'
```

### Get All Products
```bash
curl -X GET 'http://localhost:8080/api/products'
```

### Get a Specific Product
```bash
curl -X GET 'http://localhost:8080/api/products/1'
```

### Update a Product
```bash
curl -X PUT 'http://localhost:8080/api/products/1' \
-H 'Content-Type: application/json' \
-d '{
    "name": "Wireless Headphones",
    "description": "Premium noise cancelling wireless headphones",
    "price": 179.99,
    "stockQuantity": 3,
    "lowStockThreshold": 5,
    "sku": "HDN-001",
    "category": "Audio"
}'
```

### Get Low Stock Products
```bash
curl -X GET 'http://localhost:8080/api/products/low-stock'
```

### Delete a Product
```bash
curl -X DELETE 'http://localhost:8080/api/products/1'
```

## Error Handling

The API uses standard HTTP status codes:

- `200 OK`: Successful operation
- `201 Created`: Resource successfully created
- `204 No Content`: Resource successfully deleted
- `400 Bad Request`: Invalid request format or data
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server-side error

Error responses follow a consistent format:
```json
{
  "timestamp": "2023-01-15T12:34:56.789",
  "status": 404,
  "message": "Product not found with id: 100",
  "path": "/api/products/100",
  "details": null
}
```

## License

This project is licensed under the MIT License.

## Author

Boutchouang Nathan Elija - [boutchouangelija@gmail.com](mailto:boutchouangelija@gmail.com)
