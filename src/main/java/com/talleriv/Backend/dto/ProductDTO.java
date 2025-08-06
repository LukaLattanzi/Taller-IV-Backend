package com.talleriv.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Este archivo define la clase `ProductDTO` (Data Transfer Object), que es una representación
 * simplificada de un producto utilizada para la transferencia de información entre las capas de
 * la aplicación, como controladores, servicios y clientes.
 *
 * **Objetivo principal**:
 * Actuar como un medio para transportar datos relacionados con los productos en la aplicación,
 * asegurando que solo los datos relevantes sean expuestos o procesados.
 * Este diseño desacopla la capa del modelo de base de datos de la lógica de negocio y los clientes.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**:
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals` y `hashCode`.
 * - **`@AllArgsConstructor`**:
 *   - Crea un constructor con todos los campos.
 * - **`@NoArgsConstructor`**:
 *   - Crea un constructor vacío.
 * - **`@JsonInclude(JsonInclude.Include.NON_NULL)`**:
 *   - Excluye del resultado JSON los campos que son `null`, simplificando y reduciendo la salida.
 * - **`@JsonIgnoreProperties(ignoreUnknown = true)`**:
 *   - Ignora propiedades desconocidas al deserializar un objeto JSON. Esto asegura mayor
 *     flexibilidad durante la conversión de JSON a la clase `ProductDTO`.
 *
 * **Campos de la Clase**:
 * - `id`: Identificador único del producto.
 * - `productId`: ID adicional asociado al producto (por ejemplo, un identificador externo).
 * - `categoryId`: Identificador de la categoría a la que pertenece el producto.
 * - `supplierId`: Identificador del proveedor asociado al producto.
 * - `name`: Nombre del producto.
 * - `sku`: Código único de identificación del producto.
 * - `price`: Precio del producto, representado mediante `BigDecimal` para manejar valores monetarios con precisión.
 * - `stockQuantity`: Cantidad disponible del producto en el inventario.
 * - `description`: Breve descripción del producto.
 * - `imageUrl`: URL donde se encuentra la imagen asociada al producto.
 * - `expiryDate`: Fecha de vencimiento (si aplica) para el producto.
 * - `updatedAt`: Fecha de la última actualización de los datos del producto.
 * - `createdAt`: Fecha en la que se creó el producto.
 *
 * **Funcionamiento en el Proyecto**:
 * 1. **Creación o Actualización de Productos**:
 *    - La API recibe datos en formato JSON que corresponden a esta clase `ProductDTO`.
 *      Ejemplo de solicitud JSON para crear un producto:
 *      ```json
 *      {
 *          "name": "Laptop",
 *          "sku": "LAP-12345",
 *          "price": 1500.99,
 *          "stockQuantity": 10,
 *          "description": "Laptop de alta gama"
 *      }
 *      ```
 *    - Estos datos se validan y se convierten para interactuar con la capa de persistencia.
 *
 * 2. **Respuesta al Cliente**:
 *    - Cuando la aplicación envía información sobre un producto al cliente, utiliza `ProductDTO`
 *      para estructurar los datos. Esto asegura que solo los campos necesarios sean devueltos,
 *      ocultando detalles internos sensibles.
 *      Ejemplo de respuesta JSON:
 *      ```json
 *      {
 *          "id": 1,
 *          "name": "Laptop",
 *          "sku": "LAP-12345",
 *          "price": 1500.99,
 *          "stockQuantity": 10,
 *          "description": "Laptop de alta gama",
 *          "imageUrl": "/images/products/laptop.jpg",
 *          "createdAt": "2025-08-06T10:12:00"
 *      }
 *      ```
 *
 * 3. **Vinculación con Otras Clases**:
 *    - Por ejemplo, este DTO puede ser referenciado en transacciones o relaciones con
 *      categorías y proveedores. Así, se gestiona de forma organizada y clara la asignación 
 *      de categorías (`CategoryDTO`) y proveedores (`SupplierDTO`) a cada producto.
 *    - Ejemplo: Una transacción puede incluir un objeto `ProductDTO` para proporcionar 
 *      detalles de los productos relacionados con esa operación.
 *
 * **Ventajas del Uso del DTO**:
 * - **Separación de responsabilidades**:
 *   - Aísla los detalles del modelo de datos (entidad) de la capa de presentación.
 * - **Eficiencia y personalización**:
 *   - Evita incluir información innecesaria en las respuestas al cliente.
 * - **Flexibilidad**:
 *   - Permite validar datos antes de que lleguen a la base de datos.
 * - **Estandarización**:
 *   - Asegura que todas las interacciones con productos utilicen un formato uniforme.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    // Atributos del producto utilizados para intercambio de datos.
    private Long id; // ID único del producto.
    private Long productId; // ID adicional (opcional, como un identificador externo).
    private Long categoryId; // ID de la categoría asociada.
    private Long supplierId; // ID del proveedor asociado.

    private String name; // Nombre del producto.
    private String sku; // Código SKU para identificación única del producto.
    private BigDecimal price; // Precio del producto.
    private Integer stockQuantity; // Cantidad en inventario.
    private String description; // Descripción del producto.
    private String imageUrl; // URL de la imagen asociada al producto.

    private LocalDateTime expiryDate; // Fecha de vencimiento (si aplica).
    private LocalDateTime updatedAt; // Fecha última de actualización.
    private LocalDateTime createdAt; // Fecha de creación.
}