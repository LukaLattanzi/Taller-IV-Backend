package com.talleriv.Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Este archivo define la clase `LoginRequest`, que se utiliza para representar los datos necesarios 
 * en la solicitud de inicio de sesión en la aplicación.
 *
 * **Objetivo principal**:
 * Actuar como un objeto de transferencia de datos (DTO) para capturar y transportar la información 
 * requerida durante el proceso de autenticación del usuario. Esto incluye el correo electrónico 
 * y la contraseña proporcionados por el cliente.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**: 
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals` y `hashCode`.
 *   - Facilita el manejo de los datos de esta clase.
 * - **`@AllArgsConstructor`**: 
 *   - Genera un constructor que incluye todos los campos de la clase.
 * - **`@NoArgsConstructor`**: 
 *   - Genera un constructor vacío.
 * - **`@NotBlank`** (de `jakarta.validation.constraints`): 
 *   - Valida que el campo no sea nulo ni esté vacío.
 *   - Si esta restricción no se cumple, se lanza una excepción que incluye el mensaje especificado.
 *
 * **Campos de la Clase**:
 * - **`email`**: 
 *   - Correo electrónico del usuario.
 *   - Validado para asegurarse de que no sea nulo o vacío.
 *   - Requerido para identificar al usuario durante el inicio de sesión.
 * - **`password`**: 
 *   - Contraseña del usuario.
 *   - Validada para asegurarse de que no sea nula o vacía.
 *   - Requerida para verificar la autenticidad del usuario.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Captura de datos de inicio de sesión**:
 *   - Cuando un usuario intenta iniciar sesión, debe enviar un objeto en formato JSON con los campos `email` y `password`.
 *   - Ejemplo de solicitud JSON esperada:
 *     ```json
 *     {
 *         "email": "user@example.com",
 *         "password": "securePassword123"
 *     }
 *     ```
 * - **Validación de datos**:
 *   - Los datos proporcionados a esta clase serán validados antes de ser procesados.
 *   - Si alguno de los campos está vacío, se genera un error con un mensaje como:
 *     ```json
 *     {
 *         "status": 400,
 *         "message": "Email is required"
 *     }
 *     ```
 * - **Autenticación**:
 *   - Una vez validados, estos datos se pasan al servicio de autenticación, donde se verifica si las credenciales 
 *     proporcionadas son correctas.
 *
 * **Ventajas del Uso de Request DTOs**:
 * 1. **Validación Automática**:
 *    - La anotación `@NotBlank` asegura que los datos requeridos sean válidos antes de ser procesados.
 * 2. **Separación de Responsabilidades**:
 *    - Este objeto DTO aísla la estructura de las solicitudes de inicio de sesión de otros procesos en la aplicación.
 * 3. **Seguridad**:
 *    - Permite garantizar que los datos enviados contengan solo lo necesario y no expongan información sensible o innecesaria.
 *
 * **Ejemplo en el Proyecto**:
 * - Un cliente frontend llama al endpoint `/login`, enviando el correo electrónico y contraseña del usuario.
 * - El backend deserializa estos datos en una instancia de `LoginRequest`.
 * - Luego, el servicio de autenticación utiliza este objeto para verificar la existencia y validez de las credenciales en la base de datos.
 * - Si las credenciales son correctas, se devuelve un token de acceso (por ejemplo, JWT) como respuesta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    // Correo electrónico del usuario (campo obligatorio).
    @NotBlank(message = "Email is required")
    private String email;

    // Contraseña del usuario (campo obligatorio).
    @NotBlank(message = "Password is required")
    private String password;

}