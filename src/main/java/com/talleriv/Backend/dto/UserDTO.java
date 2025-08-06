package com.talleriv.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.talleriv.Backend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Este archivo define la clase `UserDTO` (Data Transfer Object), que se utiliza para representar
 * y transferir datos sobre usuarios de manera estructurada dentro de la aplicación.
 *
 * **Objetivo principal**:
 * Proporcionar una representación reducida y segura de los usuarios de la aplicación para transferir
 * datos entre las diferentes capas (controladores, servicios y APIs externas), evitando exponer la 
 * entidad completa del modelo.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**:
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals` y `hashCode`.
 *   - Esto simplifica el manejo de datos dentro de esta clase.
 * - **`@AllArgsConstructor`**:
 *   - Genera un constructor que inicializa todos los campos de la clase.
 * - **`@NoArgsConstructor`**:
 *   - Genera un constructor vacío.
 * - **`@JsonInclude(JsonInclude.Include.NON_NULL)`**:
 *   - Asegura que los campos con valores `null` no sean incluidos en las respuestas JSON,
 *     permitiendo respuestas más limpias y manejables.
 * - **`@JsonIgnoreProperties(ignoreUnknown = true)`**:
 *   - Ignora las propiedades desconocidas durante el proceso de deserialización JSON.
 *   - Esto asegura mayor flexibilidad cuando se envían datos desde el cliente que no corresponden 
 *     a los campos definidos en esta clase.
 * - **`@JsonIgnore`**:
 *   - Se utiliza para excluir el campo `password` de las respuestas JSON, garantizando la seguridad
 *     de esta información sensible.
 *
 * **Campos de la Clase**:
 * - `id`: Identificador único del usuario.
 * - `name`: Nombre completo del usuario.
 * - `email`: Dirección de correo electrónico del usuario.
 * - `password`: Contraseña del usuario. Este campo está excluido de las respuestas JSON gracias a la anotación `@JsonIgnore`.
 * - `phoneNumber`: Número de teléfono del usuario.
 * - `role`: Rol asignado al usuario, basado en el enumerador `UserRole` (por ejemplo, ADMIN, USER).
 * - `transactions`: Lista de transacciones asociadas al usuario, representadas por objetos `TransactionDTO`.
 * - `createdAt`: Fecha y hora en las que se creó el registro del usuario.
 *
 * **Funcionamiento en el Proyecto**:
 * - Este DTO es usado para recibir información cuando se crea o actualiza un usuario, y también para estructurar
 *   los datos que serán enviados como respuesta al cliente.
 * - Al interactuar con APIs, este DTO asegura que solo los datos relevantes del usuario sean enviados o procesados.
 * - Por ejemplo, al recuperar la información de un usuario, el campo `password` no será incluido en la respuesta,
 *   protegiendo datos sensibles.
 *
 * **Ejemplo de JSON de Respuesta**:
 * ```json
 * {
 *     "id": 1,
 *     "name": "Juan Pérez",
 *     "email": "juan.perez@example.com",
 *     "phoneNumber": "+123456789",
 *     "role": "USER",
 *     "transactions": [
 *         {
 *             "id": 101,
 *             "totalProducts": 3,
 *             "totalPrice": 150.75,
 *             "transactionType": "VENTA",
 *             "status": "COMPLETADA",
 *             "createdAt": "2025-08-06T08:00:00"
 *         }
 *     ],
 *     "createdAt": "2025-01-01T10:00:00"
 * }
 * ```
 * 
 * **Ventajas del Uso del DTO**:
 * 1. **Seguridad**:
 *    - La anotación `@JsonIgnore` oculta el campo `password` del JSON, asegurando que nunca se exponga 
 *      esta información sensible en las respuestas.
 * 2. **Flexibilidad**:
 *    - Ignora propiedades desconocidas al deserializar datos, permitiendo la interoperabilidad con sistemas de terceros.
 * 3. **Consistencia**:
 *    - Establece un formato estándar para manejar datos de usuarios dentro de la aplicación.
 * 4. **Reutilización**:
 *    - Incluye campos compuestos como `transactions` (que utilizan el `TransactionDTO`), facilitando la conexión entre entidades relacionadas.
 *
 * **Interacción con Clases Relacionadas**:
 * - Este DTO se conecta con el `TransactionDTO` para listar o manejar transacciones asociadas a un usuario.
 * 
 * **Ejemplo en el Proyecto**:
 * - Cuando un cliente crea una nueva cuenta de usuario, se reciben solo los campos necesarios como el nombre, 
 *   correo y contraseña. Posteriormente, al consultar la información del usuario, se devuelven datos como
 *   el rol asignado, detalles de transacciones relacionadas y la fecha de creación, sin exponer datos sensibles 
 *   como la contraseña.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    // Identificador único del usuario.
    private Long id;

    // Nombre del usuario.
    private String name;

    // Correo electrónico del usuario.
    private String email;

    // Contraseña del usuario (excluida de la salida JSON).
    @JsonIgnore
    private String password;

    // Número de teléfono del usuario.
    private String phoneNumber;

    // Rol asignado al usuario (por ejemplo, ADMIN o USER).
    private UserRole role;

    // Lista de las transacciones asociadas al usuario.
    private List<TransactionDTO> transactions;

    // Fecha y hora de la creación del usuario.
    private LocalDateTime createdAt;

}