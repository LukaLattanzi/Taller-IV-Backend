package com.talleriv.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.talleriv.Backend.enums.TransactionStatus;
import com.talleriv.Backend.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Este archivo define la clase `TransactionDTO` (Data Transfer Object), utilizada para representar
 * y transferir datos relevantes sobre transacciones dentro de la aplicación. 
 *
 * **Objetivo principal**:
 * La clase se utiliza para transportar información relacionada con transacciones (como compras, ventas 
 * u otras operaciones) entre las diferentes capas del sistema (controladores, servicios, etc.), 
 * sin necesidad de exponer directamente las entidades del modelo de datos.
 * Además, permite manipular y estructurar los datos de las transacciones de manera clara y eficiente.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**:
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals` y `hashCode`.
 *   - Simplifica el manejo de los datos dentro de la clase.
 * - **`@AllArgsConstructor`**:
 *   - Genera un constructor con todos los campos definidos.
 * - **`@NoArgsConstructor`**:
 *   - Genera un constructor vacío.
 * - **`@JsonInclude(JsonInclude.Include.NON_NULL)`**:
 *   - Excluye del resultado JSON los campos que son `null`, lo que da lugar a respuestas más limpias y compactas.
 * - **`@JsonIgnoreProperties(ignoreUnknown = true)`**:
 *   - Ignora propiedades desconocidas durante la deserialización de JSON, permitiendo mayor flexibilidad
 *     en la recepción de datos desde clientes externos.
 *
 * **Campos de la Clase**:
 * - `id`: Identificador único de la transacción.
 * - `totalProducts`: Número total de productos involucrados en la transacción.
 * - `totalPrice`: Precio total de la transacción, representado con `BigDecimal` para manejar los valores monetarios con precisión.
 * - `transactionType`: Tipo de transacción, basado en el enumerador `TransactionType` (por ejemplo, COMPRA, VENTA).
 * - `status`: Estado de la transacción, basado en el enumerador `TransactionStatus` (por ejemplo, PENDIENTE, COMPLETADA).
 * - `description`: Descripción o detalles adicionales de la transacción.
 * - `updatedAt`: Fecha y hora en la que se realizó la última actualización de la transacción.
 * - `createdAt`: Fecha y hora en la que se creó la transacción.
 * - `user`: Detalles del usuario relacionado con la transacción, representado mediante el objeto `UserDTO`.
 * - `product`: Detalles del producto asociado con la transacción, usando el objeto `ProductDTO`.
 * - `supplier`: Detalles del proveedor involucrado en la transacción, representado con el objeto `SupplierDTO`.
 *
 * **Funcionamiento en el Proyecto**:
 * 1. **Interacciones con la API**:
 *    - Este DTO es empleado tanto para recibir datos desde las solicitudes del cliente (como crear
 *      o actualizar una transacción) como para estructurar las respuestas hacia el cliente (transferir
 *      detalles de transacciones existentes).
 *    - Ejemplo de respuesta JSON:
 *      ```json
 *      {
 *          "id": 1,
 *          "totalProducts": 5,
 *          "totalPrice": 300.50,
 *          "transactionType": "COMPRA",
 *          "status": "COMPLETADA",
 *          "description": "Compra de artículos electrónicos",
 *          "createdAt": "2025-08-06T10:30:00",
 *          "updatedAt": "2025-08-06T11:00:00",
 *          "user": {
 *              "id": 10,
 *              "name": "Juan Pérez",
 *              "email": "juan.perez@example.com"
 *          },
 *          "product": {
 *              "id": 20,
 *              "name": "Laptop",
 *              "price": 1500.99
 *          },
 *          "supplier": {
 *              "id": 30,
 *              "name": "Tech Supplier Inc.",
 *              "address": "Calle Principal 123"
 *          }
 *      }
 *      ```
 * 
 * 2. **Conexión con Otros DTOs**:
 *    - El `TransactionDTO` utiliza otros DTOs relacionados (`UserDTO`, `ProductDTO` y `SupplierDTO`) 
 *      para representar y estructurar detalles de usuarios, productos y proveedores asociados a la transacción.
 *
 * 3. **Validación y Consistencia**:
 *    - Este diseño permite manejar las transacciones de manera uniforme, asegurando que todas las
 *      operaciones relacionadas con transacciones sigan el mismo formato de datos.
 *
 * **Ventajas del Uso del DTO**:
 * - **Estandarización**:
 *   - Proporciona estructura clara y uniforme para manejar datos de transacciones en toda la aplicación.
 * - **Separación de responsabilidades**:
 *   - Asegura que las entidades del modelo (que interactúan con la base de datos) no se exponen directamente al cliente.
 * - **Flexibilidad**:
 *   - Permite personalizar las respuestas JSON, incluyendo solo los datos necesarios y relevantes.
 * - **Escalabilidad**:
 *   - Facilita la adición de más campos o detalles relacionados con transacciones sin afectar la lógica central.
 *
 * **Ejemplo de Uso en el Proyecto**:
 * - Un cliente envía una solicitud para registrar una nueva transacción:
 *   ```json
 *   {
 *       "totalProducts": 3,
 *       "totalPrice": 500.75,
 *       "transactionType": "VENTA",
 *       "status": "PENDIENTE",
 *       "description": "Venta de accesorios",
 *       "user": {
 *           "id": 5
 *       },
 *       "product": {
 *           "id": 15
 *       },
 *       "supplier": {
 *           "id": 7
 *       }
 *   }
 *   ```
 * - La información se procesa y se almacena en la base de datos.
 * - Posteriormente, una solicitud para obtener el detalle de la transacción devuelve información estructurada usando este DTO.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    // Identificador único de la transacción.
    private Long id;

    // Número total de productos en la transacción.
    private Integer totalProducts;

    // Precio total de la transacción.
    private BigDecimal totalPrice;

    // Tipo de transacción (venta, compra, etc.).
    private TransactionType transactionType;

    // Estado actual de la transacción.
    private TransactionStatus status;

    // Descripción de la transacción.
    private String description;

    // Fecha de última actualización de la transacción.
    private LocalDateTime updatedAt;

    // Fecha de creación de la transacción.
    private LocalDateTime createdAt;

    // Información del usuario asociado a la transacción.
    private UserDTO user;

    // Información del producto relacionado con la transacción.
    private ProductDTO product;

    // Información del proveedor involucrado en la transacción.
    private SupplierDTO supplier;

}