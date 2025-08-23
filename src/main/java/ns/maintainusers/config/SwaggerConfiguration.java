package ns.maintainusers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Configuration
public class SwaggerConfiguration {

    // Option 1: Group API to show only UserController
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .displayName("User Management API")
                .pathsToMatch("/api/users/**")
                .build();
    }

    // Option 2: Alternative - filter by package (replace com.example.controller with your actual package)
    /*
    @Bean
    public GroupedOpenApi userApiByPackage() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .displayName("User Management API")
                .packagesToScan("com.example.controller")
                .build();
    }
    */    

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .version("1.0.0")
                        .description("A comprehensive API for managing users in the system. " +
                                "This API provides full CRUD operations for user entities including " +
                                "creation, retrieval, updating, and deletion of user records.")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("API Support Team")
                                .email("support@example.com")
                                .url("https://example.com/support"))
                        .license(new License()
                                .name("Apache License 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development server"),
                        new Server()
                                .url("https://api.example.com")
                                .description("Production server")))
                .tags(List.of(
                        new Tag()
                                .name("Users")
                                .description("Operations related to user management")))
                .components(new Components()
                        .schemas(buildSchemas())
                        .responses(buildCommonResponses()));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            // Add custom responses to all paths
            openApi.getPaths().values().forEach(pathItem -> {
                pathItem.readOperations().forEach(operation -> {
                    ApiResponses responses = operation.getResponses();
                    
                    // Add common error responses if not already present
                    if (!responses.containsKey("400")) {
                        responses.addApiResponse("400", new ApiResponse().$ref("#/components/responses/BadRequest"));
                    }
                    if (!responses.containsKey("500")) {
                        responses.addApiResponse("500", new ApiResponse().$ref("#/components/responses/InternalServerError"));
                    }
                    
                    // Add specific error responses based on operation
                    String operationId = operation.getOperationId();
                    if (operationId != null) {
                        switch (operationId) {
                            case "createUser":
                                if (!responses.containsKey("409")) {
                                    responses.addApiResponse("409", new ApiResponse().$ref("#/components/responses/UserAlreadyExists"));
                                }
                                break;
                            case "updateUser":
                            case "deleteUser":
                                if (!responses.containsKey("404")) {
                                    responses.addApiResponse("404", new ApiResponse().$ref("#/components/responses/UserNotFound"));
                                }
                                break;
                        }
                    }
                });
            });
        };
    }

    private Map<String, Schema> buildSchemas() {
        Map<String, Schema> schemas = new HashMap<>();
        
        // User schema
        Schema<?> userSchema = new Schema<>()
                .type("object")
                .description("User entity representing a system user")
                .addProperty("id", new Schema<>()
                        .type("integer")
                        .format("int64")
                        .description("Unique identifier for the user")
                        .example(1L))
                .addProperty("username", new Schema<>()
                        .type("string")
                        .description("Unique username for the user")
                        .example("john_doe")
                        .minLength(3)
                        .maxLength(50))
                .addProperty("email", new Schema<>()
                        .type("string")
                        .format("email")
                        .description("User's email address")
                        .example("john.doe@example.com"))
                .addProperty("firstName", new Schema<>()
                        .type("string")
                        .description("User's first name")
                        .example("John")
                        .maxLength(100))
                .addProperty("lastName", new Schema<>()
                        .type("string")
                        .description("User's last name")
                        .example("Doe")
                        .maxLength(100))
                .addProperty("createdAt", new Schema<>()
                        .type("string")
                        .format("date-time")
                        .description("Timestamp when the user was created")
                        .example("2023-01-01T12:00:00Z"))
                .addProperty("updatedAt", new Schema<>()
                        .type("string")
                        .format("date-time")
                        .description("Timestamp when the user was last updated")
                        .example("2023-01-01T12:00:00Z"))
                .required(List.of("username", "email"));
        
        // Error response schema
        Schema<?> errorResponseSchema = new Schema<>()
                .type("object")
                .description("Standard error response format")
                .addProperty("timestamp", new Schema<>()
                        .type("string")
                        .format("date-time")
                        .description("Timestamp when the error occurred")
                        .example("2023-01-01T12:00:00Z"))
                .addProperty("status", new Schema<>()
                        .type("integer")
                        .description("HTTP status code")
                        .example(400))
                .addProperty("error", new Schema<>()
                        .type("string")
                        .description("HTTP status text")
                        .example("Bad Request"))
                .addProperty("message", new Schema<>()
                        .type("string")
                        .description("Detailed error message")
                        .example("Invalid request parameters"))
                .addProperty("path", new Schema<>()
                        .type("string")
                        .description("API endpoint path where error occurred")
                        .example("/api/users"));
        
        schemas.put("User", userSchema);
        schemas.put("ErrorResponse", errorResponseSchema);
        
        return schemas;
    }

    private ApiResponses buildCommonResponses() {
        return new ApiResponses()
                .addApiResponse("BadRequest", new ApiResponse()
                        .description("Bad request - invalid input parameters")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                                        .example(new Example()
                                                .summary("Bad Request Example")
                                                .value("{\n" +
                                                        "  \"timestamp\": \"2023-01-01T12:00:00Z\",\n" +
                                                        "  \"status\": 400,\n" +
                                                        "  \"error\": \"Bad Request\",\n" +
                                                        "  \"message\": \"Invalid request parameters\",\n" +
                                                        "  \"path\": \"/api/users\"\n" +
                                                        "}")))))
                .addApiResponse("UserNotFound", new ApiResponse()
                        .description("User not found")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                                        .example(new Example()
                                                .summary("User Not Found Example")
                                                .value("{\n" +
                                                        "  \"timestamp\": \"2023-01-01T12:00:00Z\",\n" +
                                                        "  \"status\": 404,\n" +
                                                        "  \"error\": \"Not Found\",\n" +
                                                        "  \"message\": \"No user found with the specified ID\",\n" +
                                                        "  \"path\": \"/api/users/1\"\n" +
                                                        "}")))))
                .addApiResponse("UserAlreadyExists", new ApiResponse()
                        .description("User with the specified username already exists")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                                        .example(new Example()
                                                .summary("User Already Exists Example")
                                                .value("{\n" +
                                                        "  \"timestamp\": \"2023-01-01T12:00:00Z\",\n" +
                                                        "  \"status\": 409,\n" +
                                                        "  \"error\": \"Conflict\",\n" +
                                                        "  \"message\": \"Username already exists\",\n" +
                                                        "  \"path\": \"/api/users\"\n" +
                                                        "}")))))
                .addApiResponse("InternalServerError", new ApiResponse()
                        .description("Internal server error")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                                        .example(new Example()
                                                .summary("Internal Server Error Example")
                                                .value("{\n" +
                                                        "  \"timestamp\": \"2023-01-01T12:00:00Z\",\n" +
                                                        "  \"status\": 500,\n" +
                                                        "  \"error\": \"Internal Server Error\",\n" +
                                                        "  \"message\": \"An unexpected error occurred\",\n" +
                                                        "  \"path\": \"/api/users\"\n" +
                                                        "}")))));
    }
}