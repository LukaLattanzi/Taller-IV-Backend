package com.talleriv.Backend.security;

import com.talleriv.Backend.entity.User;
import com.talleriv.Backend.exceptions.NotFoundException;
import com.talleriv.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * La clase `CustomUserDetailsService` implementa la interfaz `UserDetailsService` de Spring Security,
 * permitiendo recuperar y mapear los detalles del usuario desde la base de datos para el proceso de autenticación.
 *
 * **Objetivo principal**:
 * Integrar el almacenamiento de usuarios en la base de datos con el mecanismo de autenticación de Spring Security.
 * Cargar a los usuarios por su correo electrónico (`email`) durante el inicio de sesión.
 *
 * **Anotaciones utilizadas**:
 * - **`@Service`**:
 *   - Marca esta clase como un componente gestionado por Spring, que será detectado como un servicio dentro del contenedor IoC.
 * - **`@RequiredArgsConstructor`**:
 *   - Genera un constructor para atributos declarados como `final`, facilitando la inyección de dependencias.
 *
 * **Atributos**:
 * - `userRepository`:
 *   - Repositorio de JPA que maneja la interacción con la tabla de usuarios en la base de datos.
 *
 * **Método principal**:
 * - `loadUserByUsername(String username)`:
 *   - Es el método de la interfaz `UserDetailsService` sobrescrito para recuperar a un usuario por su nombre de usuario (en este caso, el correo electrónico).
 *   - Lógica del método:
 *     1. Busca en la base de datos un usuario cuyo correo electrónico coincida con el argumento recibido (`username`).
 *     2. Si no encuentra al usuario, lanza una excepción personalizada (`NotFoundException`) con un mensaje claro.
 *     3. Si el usuario es encontrado, crea una instancia de `AuthUser` (clase personalizada que implementa `UserDetails`) y la retorna.
 *
 * **Funcionamiento en el Proyecto**:
 * - Este servicio se ejecuta automáticamente cada vez que Spring Security necesita autenticar a un usuario.
 * - Durante el login:
 *   1. El sistema recibe las credenciales del usuario (usualmente el correo y la contraseña).
 *   2. Invoca el método `loadUserByUsername` para recuperar la información del usuario desde la base de datos.
 *   3. Este método retorna un objeto `AuthUser`, que contiene los detalles del usuario (correo, contraseña y roles).
 *   4. Spring Security valida la contraseña y configura el contexto con el usuario autenticado.
 *
 * **Excepciones**:
 * - Si el correo electrónico no se encuentra en la base de datos:
 *   - Lanza una `NotFoundException`, que posteriormente puede ser tratada por un manejador de excepciones global para enviar una respuesta adecuada al cliente.
 *
 * **Ejemplo de Uso en el Proyecto**:
 * - Cuando un usuario intenta autenticarse mediante un token JWT o las credenciales de inicio de sesión,
 *   este servicio se encarga de recuperar la información del usuario y validar su existencia en la base de datos.
 *
 * **Ventajas de esta Implementación**:
 * 1. **Integración Transparente**:
 *    - Aprovecha la interfaz estándar `UserDetailsService` de Spring, lo que facilita la personalización y reutilización dentro del ecosistema de seguridad.
 * 2. **Excepciones Claras**:
 *    - El uso de la excepción personalizada `NotFoundException` simplifica el manejo de errores en el controlador.
 * 3. **Flexibilidad**:
 *    - La clase permite extender la lógica de recuperación de usuarios con validaciones específicas o adaptaciones en el futuro.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // Repositorio JPA para acceder a los usuarios almacenados en la base de datos.
    private final UserRepository userRepository;

    /**
     * Sobrescribe el método `loadUserByUsername` para cargar usuarios por su correo electrónico.
     *
     * @param username El correo electrónico del usuario que se desea cargar.
     * @return Un objeto `UserDetails` que representa al usuario autenticado.
     * @throws UsernameNotFoundException Si el usuario no es encontrado en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar al usuario por correo electrónico en la base de datos.
        User user = userRepository.findByEmail(username)
                // Si no se encuentra al usuario, se lanza una excepción.
                .orElseThrow(() -> new NotFoundException("User Email Not Found"));

        // Retornar un objeto `AuthUser` con los detalles del usuario.
        return AuthUser.builder()
                .user(user)
                .build();
    }
}

