# e-Shop - Showcase E-Commerce Application

## Overview
This project showcases a basic e-commerce application built with Spring Boot. It demonstrates functionalities for managing products and categories in an online store.

**Important Note:** This is a showcase application and doesn't implement features like user authentication, shopping cart, or payment processing.

## Technologies Used
- Java 17 (or later)
- Spring Boot 3.3.5
- Spring Data JPA (for database access)
- Spring Security (included but not configured)
- Kotlin (optional)
- Lombok (optional, recommended for cleaner code)
- ModelMapper (for object mapping)
- PostgreSQL (supported database)
- H2 Database (for in-memory testing)

## Project Structure
The project is organized with the following main packages:
- `com.keeghan.eShop.domain.entities`: Domain entities like `Product` and `Category`
- `com.keeghan.eShop.repositories`: Repositories for database interactions
- `com.keeghan.eShop.service`: Services for managing products and categories

## Getting Started

### Prerequisites
- Java 17 or later
- Maven

### Installation
1. Clone the repository
2. Open terminal in project directory
3. Run `mvn clean install`
4. (Optional) Update `application.properties` with database connection details
5. Start the application with `mvn spring-boot:run`

## Available Endpoints
- **GET `/products/{id}`**: Retrieve a product by ID
- **POST `/products`**: Create a new product
- **PUT `/products/{id}`**: Update an existing product
- **DELETE `/products/{id}`**: Delete a product
- **POST `/products/{categoryId}/add`**: Add a product to a category
- **PUT `/products/{productId}/remove`**: Remove a product from its category

## API Documentation
Detailed API documentation can be generated using tools like Swagger (not included in this showcase).

## Notes
- Spring Security is included but not configured
- Kotlin integration available for development flexibility
- Consider using Lombok for reducing boilerplate code

## License
No specific license applied. Consider MIT or Apache 2.0 for future development.