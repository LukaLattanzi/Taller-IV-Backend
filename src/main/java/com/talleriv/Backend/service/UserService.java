package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.LoginRequest;
import com.talleriv.Backend.dto.RegisterRequest;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.UserDTO;
import com.talleriv.Backend.entity.User;

/**
 * La interfaz `UserService` define las operaciones relacionadas con la gestión de usuarios en la aplicación. 
 * Esto incluye procesos de registro, inicio de sesión, modificación y eliminación de usuarios, así como la recuperación 
 * de datos específicos asociados a los usuarios.
 *
 * **Objetivo principal**:
 * Proveer un contrato claro para implementar servicios relacionados con los usuarios, garantizando una separación 
 * adecuada de las responsabilidades y un diseño consistente.
 *
 * **Métodos Declarados**:
 *
 * 1. **`registerUser(RegisterRequest registerRequest)`**:
 *    - Registra un nuevo usuario en el sistema.
 *    - Recibe un objeto `RegisterRequest` que contiene los datos del registro.
 *    - Retorna un objeto `Response` con el estado y resultado del registro.
 *
 * 2. **`loginUser(LoginRequest loginRequest)`**:
 *    - Autentica un usuario basado en las credenciales proporcionadas.
 *    - Recibe un objeto `LoginRequest` con el email y contraseña.
 *    - Devuelve un objeto `Response` que incluye los detalles de autenticación (como un token de sesión).
 *
 * 3. **`getAllUsers()`**:
 *    - Recupera la lista de todos los usuarios registrados en el sistema.
 *    - Retorna un objeto `Response` conteniendo la lista de usuarios o el estado de la operación.
 *
 * 4. **`getCurrentLoggedInUser()`**:
 *    - Devuelve los detalles del usuario actualmente autenticado.
 *    - Retorna un objeto de tipo `User` que representa al usuario conectado.
 *
 * 5. **`updateUser(Long id, UserDTO userDTO)`**:
 *    - Permite actualizar la información de un usuario existente.
 *    - Recibe el `id` del usuario a actualizar y un objeto `UserDTO` con los nuevos datos.
 *    - Devuelve un objeto `Response` reflejando el estado de la operación.
 *
 * 6. **`deleteUser(Long id)`**:
 *    - Elimina un usuario del sistema identificado por su `id`.
 *    - Retorna un objeto `Response` confirmando la eliminación o indicando un error en caso de que ocurra.
 *
 * 7. **`getUserTransactions(Long id)`**:
 *    - Recupera todas las transacciones realizadas por un usuario específico.
 *    - Recibe el `id` del usuario.
 *    - Devuelve un `Response` con la lista de transacciones asociadas al usuario.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Relaciones con otras capas**:
 *   - Interfaces como `UserService` son utilizadas por controladores REST para manejar peticiones relacionadas 
 *     con usuarios en la API.
 *   - La implementación (`UserServiceImpl`) interactúa con la capa de persistencia (repositorios) y otras clases de servicio.
 * - **Seguridad y Autenticación**:
 *   - Métodos como `loginUser` o `getCurrentLoggedInUser` involucran autenticación y generación de tokens, importantes 
 *     para garantizar la seguridad del sistema.
 *
 * **Ventajas del diseño basado en la interfaz**:
 * 1. **Desacoplamiento**:
 *    - Permite separar las responsabilidades de lógica de negocio de los usuarios de otras partes del sistema.
 * 
 * 2. **Escalabilidad**:
 *    - Hace posible la adición de nuevas funcionalidades o modificaciones sin afectar a controladores 
 *      u otras partes dependientes de la interfaz.
 * 
 * 3. **Reutilización y consistencia**:
 *    - Métodos como `updateUser` y `deleteUser` pueden ser utilizados por diferentes partes del sistema, 
 *      asegurando un comportamiento uniforme.
 *
 * **Ejemplo práctico**:
 * - **Actualizar un usuario** (`updateUser`):
 *   1. Validar los datos proporcionados en el `UserDTO`.
 *   2. Recuperar el usuario existente desde el repositorio.
 *   3. Modificar los campos según los datos del `UserDTO`.
 *   4. Guardar los cambios en la base de datos.
 *   5. Retornar un objeto `Response` confirmando el éxito.
 */
public interface UserService {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param registerRequest Objeto con los datos del usuario a registrar.
     * @return Un objeto `Response` indicando el estado del registro.
     */
    Response registerUser(RegisterRequest registerRequest);

    /**
     * Inicia sesión autenticando a un usuario.
     *
     * @param loginRequest Contiene las credenciales necesarias para autenticación.
     * @return Respuesta conteniendo el estado de autenticación y, opcionalmente, un token.
     */
    Response loginUser(LoginRequest loginRequest);

    /**
     * Recupera todos los usuarios registrados.
     *
     * @return Una respuesta con la lista de todos los usuarios.
     */
    Response getAllUsers();

    /**
     * Obtiene el usuario actualmente autenticado en la sesión.
     *
     * @return Objeto `User` del usuario conectado.
     */
    User getCurrentLoggedInUser();

    /**
     * Actualiza datos de un usuario existente.
     *
     * @param id Identificador del usuario a actualizar.
     * @param userDTO Detalles del usuario a modificar.
     * @return Respuesta con el estado de la operación.
     */
    Response updateUser(Long id, UserDTO userDTO);

    /**
     * Elimina un usuario del sistema basado en su ID.
     *
     * @param id Identificador del usuario a eliminar.
     * @return Respuesta confirmando o rechazando la eliminación.
     */
    Response deleteUser(Long id);

    /**
     * Obtiene todas las transacciones asociadas a un usuario específico.
     *
     * @param id ID del usuario.
     * @return Respuesta que contiene la lista de transacciones.
     */
    Response getUserTransactions(Long id);
}