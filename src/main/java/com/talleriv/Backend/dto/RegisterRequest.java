package com.talleriv.Backend.dto;

import com.talleriv.Backend.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Este archivo define la clase `RegisterRequest`, que se utiliza para manejar los datos necesarios
 * en el proceso de registro de un nuevo usuario en la aplicación.
 *
 * **Objetivo principal**:
 * Actuar como un objeto de transferencia de datos (DTO) que encapsula la información requerida
 * para el registro de usuarios y permite validar y transportar estos datos de manera estructurada
 * y segura entre el frontend y el backend.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**:
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals` y `hashCode`.
 *   - Facilita el manejo de los datos en esta clase.
 * - **`@AllArgsConstructor`**:
 *   - Genera un constructor que inicializa todos los campos de la clase.
 * - **`@NoArgsConstructor`**:
 *   - Genera un constructor vacío.
 * - **`@NotBlank`** (de `jakarta.validation.constraints`):
 *   - Valida que cada campo anotado no sea nulo ni esté vacío. Si la validación falla,
 *     se proporciona el mensaje de error especificado.
 *
 * **Campos de la Clase**:
 * - **`name`**:
 *   - Nombre completo del usuario.
 *   - Validado para asegurarse de que no sea nulo ni vacío.
 *   - Campo obligatorio para identificar a un usuario.
 * - **`email`**:
 *   - Dirección de correo electrónico del usuario.
 *   - Validado para asegurarse de que no sea nulo ni vacío.
 *   - Debe ser único en el sistema y se utiliza también para la autenticación.
 * - **`password`**:
 *   - Contraseña del usuario.
 *   - Validada para asegurarse de que no sea nula ni vacía.
 *   - En el backend generalmente será encriptada antes de almacenarse.
 * - **`phoneNumber`**:
 *   - Número de teléfono del usuario.
 *   - Validado para asegurarse de que no sea nulo ni vacío.
 *   - Adicional para contacto del usuario.
 * - **`role`**:
 *   - Rol del usuario en la aplicación, basado en el enumerador `UserRole` (por ejemplo, ADMIN, USER).
 *   - Aunque es opcional, puede ser solicitado para establecer permisos específicos durante el registro.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Captura de los datos para registro**:
 *   - Un cliente (como una aplicación frontend) envía los detalles del nuevo usuario en un formato JSON
 *     que será deserializado a una instancia de `RegisterRequest`.
 *   - Ejemplo de JSON esperado para la solicitud:
 *     ```json
 *     {
 *         "name": "Juan Pérez",
 *         "email": "juan.perez@example.com",
 *         "password": "miPasswordSeguro123",
 *         "phoneNumber": "+123456789",
 *         "role": "USER"
 *     }
 *     ```
 * - **Validación**:
 *   - Los valores se validan utilizando las anotaciones `@NotBlank`. Si algún campo obligatorio está vacío,
 *     se genera un error de validación, por ejemplo:
 *     ```json
 *     {
 *         "status": 400,
 *         "message": "Name is required"
 *     }
 *     ```
 * - **Creación del usuario**:
 *   - Una vez validados los datos, se utilizan para crear un nuevo usuario en la base de datos.
 *     El campo `password` será encriptado para garantizar la seguridad.
 *
 * **Ventajas del Uso de Request DTOs**:
 * 1. **Validación Automática**:
 *    - Garantiza que los datos recibidos sean válidos antes de ser procesados, reduciendo errores en las capas posteriores.
 * 2. **Separación de Responsabilidades**:
 *    - Aísla la estructura de los datos de registro del modelo de usuario y otras partes del sistema.
 * 3. **Escalabilidad**:
 *    - Es fácil agregar nuevos campos o reglas de validación en el DTO sin afectar directamente otras partes del sistema.
 *
 * **Ejemplo en el Proyecto**:
 * - Un cliente frontend envía los datos de registro a un endpoint como `/register`.
 * - El backend utiliza este DTO para extraer los datos validados y crear al nuevo usuario.
 * - Después de completar el registro, el sistema puede devolver una respuesta exitosa al cliente.
 *
 * **Respuesta JSON** (en caso de éxito):
 * ```json
 * {
 *     "status": 201,
 *     "message": "Usuario registrado exitosamente"
 * }
 * ```
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // Nombre completo del usuario (campo obligatorio).
    @NotBlank(message = "Name is required")
    private String name;

    // Dirección de correo electrónico (campo obligatorio).
    @NotBlank(message = "Email is required")
    private String email;

    // Contraseña del usuario (campo obligatorio).
    @NotBlank(message = "Password is required")
    private String password;

    // Número de teléfono del usuario (campo obligatorio).
    @NotBlank(message = "PhoneNumber is required")
    private String phoneNumber;

    // Rol asignado al usuario (opcional, ejemplos: ADMIN o USER).
    private UserRole role;
}
