# Microservicio Catálogo-Producto

Microservicio RESTful para la gestión de catálogos de productos y categorías, desarrollado con Spring Boot 3.5.7 y arquitectura de microservicios.

## Tecnologías

- Java 17
- Spring Boot 3.5.7
- Spring Data JPA
- MySQL 8.0
- Lombok
- Maven

## Requisitos Previos

- JDK 17 o superior
- Maven 3.6+
- MySQL 8.0+
- Postman (opcional, para pruebas)

## Arquitectura

El proyecto sigue una arquitectura en capas con separación de responsabilidades:

src/main/java/com/levelup/catalogo_producto/
├── model/ - Entidades JPA (Category, Product)
├── repository/ - Interfaces de acceso a datos
├── dto/ - Objetos de transferencia de datos
├── service/ - Lógica de negocio y conversiones DTO-Entity
└── controller/ - Endpoints REST (solo usan DTOs)

### Relaciones JPA

- **Category** (1) ↔ (N) **Product**: Relación OneToMany/ManyToOne
- Category puede tener múltiples productos
- Cada producto pertenece a una categoría

## Configuración

### Base de Datos

Crear la base de datos en MySQL:

CREATE DATABASE catalogo_db;

### application.properties

spring.application.name=catalogo-producto
server.port=8082

spring.datasource.url=jdbc:mysql://localhost:3306/catalogo_db
spring.datasource.username=root
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

## Instalación y Ejecución

### Compilar el proyecto

mvn clean install

### Ejecutar la aplicación

mvn spring-boot:run

La aplicación estará disponible en: `http://localhost:8082`

## API Endpoints

### Categorías

| Método | Endpoint               | Descripción                  |
| ------ | ---------------------- | ---------------------------- |
| POST   | `/api/categories`      | Crear categoría              |
| GET    | `/api/categories`      | Obtener todas las categorías |
| GET    | `/api/categories/{id}` | Obtener categoría por ID     |
| PUT    | `/api/categories/{id}` | Actualizar categoría         |
| DELETE | `/api/categories/{id}` | Eliminar categoría           |

### Productos

| Método | Endpoint                              | Descripción                     |
| ------ | ------------------------------------- | ------------------------------- |
| POST   | `/api/products`                       | Crear producto                  |
| GET    | `/api/products`                       | Obtener todos los productos     |
| GET    | `/api/products/{id}`                  | Obtener producto por ID         |
| GET    | `/api/products/category/{categoryId}` | Obtener productos por categoría |
| PUT    | `/api/products/{id}`                  | Actualizar producto             |
| DELETE | `/api/products/{id}`                  | Eliminar producto               |

## Ejemplos de Uso

### Crear una Categoría

**Request:**
POST http://localhost:8082/api/categories
Content-Type: application/json

{
"name": "Electrónica",
"description": "Productos electrónicos y gadgets"
}

**Response:**
{
"id": 1,
"name": "Electrónica",
"description": "Productos electrónicos y gadgets"
}

### Crear un Producto

**Request:**
POST http://localhost:8082/api/products
Content-Type: application/json

{
"name": "Laptop HP Pavilion",
"description": "Laptop HP 15 pulgadas, 8GB RAM, 256GB SSD",
"price": 599.99,
"stock": 10,
"categoryId": 1
}

**Response:**
{
"id": 1,
"name": "Laptop HP Pavilion",
"description": "Laptop HP 15 pulgadas, 8GB RAM, 256GB SSD",
"price": 599.99,
"stock": 10,
"categoryId": 1,
"categoryName": "Electrónica"
}

### Obtener Productos por Categoría

**Request:**
GET http://localhost:8082/api/products/category/1

**Response:**
[
{
"id": 1,
"name": "Laptop HP Pavilion",
"description": "Laptop HP 15 pulgadas, 8GB RAM, 256GB SSD",
"price": 599.99,
"stock": 10,
"categoryId": 1,
"categoryName": "Electrónica"
}
]

## Validaciones

Los DTOs incluyen validaciones con Jakarta Validation:

- `@NotBlank`: Campos obligatorios
- `@NotNull`: Valores no nulos
- `@Positive`: Números positivos

## Pruebas con Postman

Importar la colección incluida: `Catalogo_Producto_Postman_Collection.json`

La colección incluye:

- 7 pruebas de categorías (CRUD completo)
- 11 pruebas de productos (CRUD completo + filtros)
- 5 pruebas de validación y manejo de errores

## Estructura de Base de Datos

### Tabla categories

id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(100) NOT NULL UNIQUE
description VARCHAR(500)

### Tabla products

id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(200) NOT NULL
description VARCHAR(1000)
price DECIMAL(10,2) NOT NULL
stock INT NOT NULL
category_id BIGINT NOT NULL
FOREIGN KEY (category_id) REFERENCES categories(id)

## Notas de Desarrollo

- El microservicio utiliza el package `model` en lugar de `entity` para las entidades JPA
- Todos los packages están en minúsculas siguiendo convenciones de Java
- Se implementa separación completa entre capas (Controller → Service → Repository)
- Los controllers no tienen acceso directo a las entidades del modelo
