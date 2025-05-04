package com.nathan.inventory_management_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * OpenAPI Configuration for the Inventory Management API.
 *
 * This class configures the OpenAPI documentation (Swagger) for the API.
 * It provides metadata about the API, contact information, license details,
 * and server information, which are displayed in the Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures and creates a custom OpenAPI bean.
     *
     * This bean is used by SpringDoc to generate the OpenAPI documentation
     * and the Swagger UI interface.
     *
     * @return A configured OpenAPI object that describes the API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // Create contact information
        Contact contact = new Contact()
                .email("boutchouangelija@gmail.com")
                .name("Boutchouang Nathan Elija")
                .url("https://www.facebook.com/profile.php?id=100093308405031");

        // Create license information
        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        // Define server
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local Development Server");

        // Create and return a customized OpenAPI instance
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Management API")
                        .version("1.0")
                        .contact(contact)
                        .description("API for managing an inventory of products with stock tracking. " +
                                "This API allows users to create, read, update, and delete products, " +
                                "as well as receive alerts for low stock items.")
                        .license(mitLicense))
                .components(new Components())
                .servers(Arrays.asList(localServer));
    }
}
