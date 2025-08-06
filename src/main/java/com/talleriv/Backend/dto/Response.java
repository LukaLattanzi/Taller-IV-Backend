package com.talleriv.Backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.talleriv.Backend.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Este archivo define la clase `Response`, que se utiliza para estructurar y estandarizar las respuestas 
 * proporcionadas por el backend de la aplicación a las solicitudes realizadas por los clientes.
 *
 * **Objetivo principal**:
 * Actuar como un contenedor versátil y flexible para construir respuestas HTTP que incluyan tanto 
 * información relevante del resultado como detalles adicionales, dependiendo del tipo de operación realizada.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**: 
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals`, y `hashCode`.
 *   - Facilita un manejo limpio de los datos dentro de esta clase.
 * - **`@Builder`**: 
 *   - Permite utilizar el patrón Builder para construir instancias de esta clase de manera sencilla y fluida.
 * - **`@JsonInclude(JsonInclude.Include.NON_NULL)`**:
 *   - Configura el JSON que se retorna, excluyendo los campos que tienen valores `null`.
 *   - Asegura respuestas más compactas y claras, mostrando solo los campos relevantes para cada caso.
 *
 * **Campos de la Clase**:
 * - **Genéricos**:
 *   - `status`: Código de estado HTTP asociado a esta respuesta (por ejemplo, 200, 404, 500).
 *   - `message`: Mensaje descriptivo que detalla la información clave relacionada con el resultado de la solicitud.
 * - **Login**:
 *   - `token`: Token de autenticación (por ejemplo, un JWT) retornado tras un inicio de sesión exitoso.
 *   - `role`: Rol asociado al usuario autenticado (por ejemplo, ADMIN o USER).
 *   - `expirationTime`: Información sobre cuándo expira el token de autenticación.
 * - **Paginación**:
 *   - `totalPages`: Número total de páginas en la paginación de datos.
 *   - `totalElements`: Número total de elementos disponibles en la respuesta paginada.
 * - **Datos Opcionales (según contexto de la respuesta)**:
 *   - **Usuario** (`user`, `users`): Detalles de usuario o lista de usuarios (basado en `UserDTO`).
 *   - **Proveedor** (`supplier`, `suppliers`): Información relacionada con un proveedor o lista de proveedores (basado en `SupplierDTO`).
 *   - **Categoría** (`category`, `categories`): Detalles sobre una categoría o una lista de categorías (basado en `CategoryDTO`).
 *   - **Producto** (`product`, `products`): Datos de producto o lista de productos (usando `ProductDTO`).
 *   - **Transacción** (`transaction`, `transactions`): Información de una transacción o varias transacciones (a partir de `TransactionDTO`).
 * - **Timestamp**:
 *   - `timestamp`: Marca temporal de cuándo se genera esta respuesta, útil para propósitos de auditoría o seguimiento.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Unificación de Respuestas**:
 *   - La clase `Response` asegura un formato consistente de las respuestas enviadas desde el backend al cliente.
 *   - Esto simplifica el desarrollo en el frontend, ya que los clientes de la API siempre esperan una estructura predecible.
 * - **Flexibilidad**:
 *   - Dependiendo del tipo de operación o solicitud, los datos relevantes serán incluidos en los campos correspondientes. 
 *     Por ejemplo:
 *     - Para un inicio de sesión exitoso, se incluyen `token`, `role`, y `expirationTime`.
 *     - Para una consulta de transacciones, se incluyen campos como `transaction` o `transactions`.
 * - **Casos de Uso Específicos**:
 *   - **Autenticación**:
 *     - La respuesta de un inicio de sesión exitoso podría lucir así:
 *       ```json
 *       {
 *           "status": 200,
 *           "message": "Login successful",
 *           "token": "eyJhbGciOiJIUzI1NiIsInR...",
 *           "role": "ADMIN",
 *           "expirationTime": "2025-08-07T10:00:00",
 *           "timestamp": "2025-08-06T10:30:00"
 *       }
 *       ```
 *   - **Paginación de Resultados**:
 *     - La respuesta para una consulta paginada de usuarios podría lucir así:
 *       ```json
 *       {
 *           "status": 200,
 *           "message": "Users retrieved successfully",
 *           "totalPages": 5,
 *           "totalElements": 50,
 *           "users": [
 *               {"id": 1, "name": "Juan Pérez", "email": "juan.perez@example.com"},
 *               {"id": 2, "name": "Ana Gómez", "email": "ana.gomez@example.com"}
 *           ],
 *           "timestamp": "2025-08-06T10:35:00"
 *       }
 *       ```
 *   - **Detalle de Transacción**:
 *     - Al consultar una transacción específica:
 *       ```json
 *       {
 *           "status": 200,
 *           "message": "Transaction details retrieved successfully",
 *           "transaction": {
 *               "id": 101,
 *               "totalPrice": 250.00,
 *               "description": "Compra de oficina",
 *               "createdAt": "2025-08-05T09:00:00"
 *           },
 *           "timestamp": "2025-08-06T11:00:00"
 *       }
 *       ```
 *
 * **Ventajas del Uso de una Clase `Response`**:
 * 1. **Consistencia**:
 *    - Todas las respuestas siguen un formato uniforme, lo que facilita tanto el desarrollo como el mantenimiento del cliente.
 * 2. **Flexibilidad**:
 *    - Se pueden incluir únicamente los campos relevantes para cada operación, manteniendo las respuestas ligeras.
 * 3. **Legibilidad**:
 *    - El campo `message` proporciona una descripción clara de los resultados de cada operación.
 * 4. **Seguimiento**:
 *    - La inclusión del campo `timestamp` permite rastrear cuándo se generó una respuesta, lo cual es útil para auditorías o análisis.
 *
 * **Conclusión**:
 * - La clase `Response` es fundamental para proporcionar respuestas claras, estructuradas y fáciles de consumir 
 *   por el cliente, independientemente de la operación realizada en el backend.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    // Código de estado HTTP (ejemplo: 200, 400, 404).
    private int status;

    // Mensaje descriptivo del resultado de la operación.
    private String message;

    // Token de autenticación, utilizado en las operaciones de inicio de sesión.
    private String token;

    // Rol del usuario (ejemplo: ADMIN, USER), relevante en autenticación.
    private UserRole role;

    // Tiempo de expiración del token de autenticación.
    private String expirationTime;

    // Total de páginas para una consulta con paginación.
    private Integer totalPages;

    // Número total de elementos disponibles en una consulta con paginación.
    private Long totalElements;

    // Información sobre un usuario o una lista de usuarios.
    private UserDTO user;
    private List<UserDTO> users;

    // Información sobre un proveedor o una lista de proveedores.
    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;

    // Información sobre una categoría o una lista de categorías.
    private CategoryDTO category;
    private List<CategoryDTO> categories;

    // Información sobre un producto o una lista de productos.
    private ProductDTO product;
    private List<ProductDTO> products;

    // Información sobre una transacción o una lista de transacciones.
    private TransactionDTO transaction;
    private List<TransactionDTO> transactions;

    // Marca temporal del momento en que se genera esta respuesta.
    private final LocalDateTime timestamp = LocalDateTime.now();
}